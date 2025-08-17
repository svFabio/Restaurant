package com.restaurante.pos.service;

import com.restaurante.pos.dto.CreateDailyMenuDTO;
import com.restaurante.pos.dto.DailyMenuDTO;
import com.restaurante.pos.dto.DailyMenuDishDTO;
import com.restaurante.pos.entity.DailyMenu;
import com.restaurante.pos.entity.DailyMenuDish;
import com.restaurante.pos.entity.Dish;
import com.restaurante.pos.repository.DailyMenuRepository;
import com.restaurante.pos.repository.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private DailyMenuRepository dailyMenuRepository;
    @Autowired
    private DishRepository dishRepository;

    @Transactional
    public DailyMenuDTO createDailyMenu(CreateDailyMenuDTO createDto) {
        dailyMenuRepository.findByMenuDate(createDto.getMenuDate()).ifPresent(menu -> {
            throw new IllegalStateException("Ya existe un menú para la fecha: " + createDto.getMenuDate());
        });

        DailyMenu dailyMenu = new DailyMenu();
        dailyMenu.setMenuDate(createDto.getMenuDate());

        List<DailyMenuDish> menuDishes = new ArrayList<>();
        for (DailyMenuDishDTO dishDto : createDto.getDishes()) {
            Dish dish = dishRepository.findById(dishDto.getDishId())
                    .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado con id: " + dishDto.getDishId()));

            DailyMenuDish menuDish = new DailyMenuDish();
            menuDish.setDish(dish);

            menuDish.setInitialStock(dishDto.getInitialStock());
            menuDish.setCurrentStock(dishDto.getInitialStock());
            menuDish.setDailyMenu(dailyMenu);
            menuDishes.add(menuDish);
        }

        dailyMenu.setDailyMenuDishes(menuDishes);
        DailyMenu savedMenu = dailyMenuRepository.save(dailyMenu);
        return convertToDTO(savedMenu);
    }

    @Transactional(readOnly = true)
    public Optional<DailyMenuDTO> getMenuByDate(LocalDate date) {
        return dailyMenuRepository.findByMenuDate(date)
                .map(this::convertToDTO);
    }

    // ÚNICA VERSIÓN CORRECTA DEL MÉTODO
    private DailyMenuDTO convertToDTO(DailyMenu menu) {
        DailyMenuDTO menuDto = new DailyMenuDTO();
        menuDto.setId(menu.getId());
        menuDto.setMenuDate(menu.getMenuDate());

        List<DailyMenuDishDTO> dishDtos = menu.getDailyMenuDishes().stream().map(menuDish -> {
            DailyMenuDishDTO dishDto = new DailyMenuDishDTO();
            dishDto.setId(menuDish.getId()); // <-- La corrección importante
            dishDto.setDishId(menuDish.getDish().getId());
            dishDto.setDishName(menuDish.getDish().getName());
            dishDto.setInitialStock(menuDish.getInitialStock());
            dishDto.setCurrentStock(menuDish.getCurrentStock());
            dishDto.setPrice(menuDish.getDish().getPrice());
            dishDto.setCategoryName(menuDish.getDish().getCategory().getName());
            return dishDto;
        }).collect(Collectors.toList());

        menuDto.setDishes(dishDtos);
        return menuDto;
    }
}