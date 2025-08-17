package com.restaurante.pos.service;

import com.restaurante.pos.dto.DishCategoryDTO;
import com.restaurante.pos.entity.DishCategory;
import com.restaurante.pos.repository.DishCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishCategoryService {

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    public DishCategoryDTO createCategory(DishCategory category) {
        category.setId(null);
        DishCategory savedCategory = dishCategoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    public List<DishCategoryDTO> getAllCategories() {
        return dishCategoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DishCategoryDTO convertToDTO(DishCategory category) {
        DishCategoryDTO dto = new DishCategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setActive(category.isActive());
        return dto;
    }
    public DishCategoryDTO toggleCategoryStatus(Long id) {
        DishCategory category = dishCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));

        // Invertimos el estado actual (si es true, se vuelve false, y viceversa)
        category.setActive(!category.isActive());

        DishCategory updatedCategory = dishCategoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }
}