package com.restaurante.pos.service;

import com.restaurante.pos.dto.DishDTO;
import com.restaurante.pos.entity.Dish;
import com.restaurante.pos.entity.DishCategory;
import com.restaurante.pos.repository.DishCategoryRepository;
import com.restaurante.pos.repository.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    // Método para obtener todos los platos
    @Transactional(readOnly = true) // Transacción de solo lectura, es más eficiente
    public List<DishDTO> findAll() {
        return dishRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para crear un nuevo plato
    @Transactional
    public DishDTO save(DishDTO dishDTO) {
        // Buscamos la categoría por su ID
        DishCategory category = dishCategoryRepository.findById(dishDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + dishDTO.getCategoryId()));

        // Creamos una nueva entidad Dish
        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setDescription(dishDTO.getDescription());
        dish.setActive(dishDTO.isActive());
        dish.setCategory(category);

        // Guardamos el nuevo plato en la base de datos
        Dish savedDish = dishRepository.save(dish);

        // Convertimos la entidad guardada de nuevo a DTO para devolverla
        return convertToDTO(savedDish);
    }


    // Método privado para convertir una Entidad Dish a un DishDTO
    private DishDTO convertToDTO(Dish dish) {
        DishDTO dto = new DishDTO();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setActive(dish.isActive());
        dto.setCategoryId(dish.getCategory().getId());
        dto.setCategoryName(dish.getCategory().getName());
        return dto;
    }
}