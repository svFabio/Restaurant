package com.restaurante.pos.controller;

import com.restaurante.pos.dto.DishDTO;
import com.restaurante.pos.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurante.pos.dto.CreateDishDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;



@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }
    // Aquí añadiremos el método POST para crear platos en el siguiente paso

    @PostMapping
    public ResponseEntity<DishDTO> createDish(@RequestBody CreateDishDTO createDishDTO) {
        DishDTO createdDish = dishService.createDish(createDishDTO);
        return ResponseEntity.ok(createdDish);
    }
    }