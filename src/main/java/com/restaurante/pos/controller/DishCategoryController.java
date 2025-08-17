package com.restaurante.pos.controller;

import com.restaurante.pos.dto.DishCategoryDTO;
import com.restaurante.pos.entity.DishCategory;
import com.restaurante.pos.service.DishCategoryService; // <-- Usamos el nuevo servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class DishCategoryController {

    @Autowired
    private DishCategoryService dishCategoryService; // <-- Inyectamos el servicio

    @GetMapping
    public ResponseEntity<List<DishCategoryDTO>> getAllCategories() {
        List<DishCategoryDTO> categories = dishCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<DishCategoryDTO> createCategory(@RequestBody DishCategory category) {
        DishCategoryDTO savedCategory = dishCategoryService.createCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<DishCategoryDTO> toggleCategoryStatus(@PathVariable Long id) {
        DishCategoryDTO updatedCategory = dishCategoryService.toggleCategoryStatus(id);
        return ResponseEntity.ok(updatedCategory);
    }

}