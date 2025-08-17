package com.restaurante.pos.service;

import com.restaurante.pos.dto.CreateDishDTO;
import com.restaurante.pos.dto.DishDTO;
import com.restaurante.pos.entity.Dish;
import com.restaurante.pos.entity.DishCategory;
import com.restaurante.pos.repository.DishCategoryRepository;
import com.restaurante.pos.repository.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    public List<DishDTO> getAllDishes() {
        List<Dish> dishesFromDb = dishRepository.findAll();

        // --- LOG DE DIAGNÓSTICO ---
        System.out.println("--- VERIFICANDO PLATOS DESDE EL SERVICIO ---");
        dishesFromDb.forEach(dish -> {
            System.out.println("Plato: " + dish.getName() + " | Precio leído por Hibernate: " + dish.getPrice());
        });
        System.out.println("--------------------------------------------");

        return dishesFromDb.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DishDTO createDish(CreateDishDTO createDishDTO) {
        DishCategory category = dishCategoryRepository.findById(createDishDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + createDishDTO.getCategoryId()));

        Dish newDish = new Dish();
        newDish.setName(createDishDTO.getName());
        newDish.setDescription(createDishDTO.getDescription());
        newDish.setPrice(createDishDTO.getPrice());
        newDish.setActive(createDishDTO.isActive());
        newDish.setCategory(category);

        Dish savedDish = dishRepository.save(newDish);
        return convertToDTO(savedDish);
    }

    private DishDTO convertToDTO(Dish dish) {
        DishDTO dto = new DishDTO();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setPrice(dish.getPrice()); // <-- ¡¡LA LÍNEA QUE FALTABA!!
        dto.setActive(dish.isActive());
        dto.setCategoryName(dish.getCategory().getName());
        return dto;
    }
}