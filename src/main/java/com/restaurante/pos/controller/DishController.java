package com.restaurante.pos.controller;

import com.restaurante.pos.dto.DishDTO;
import com.restaurante.pos.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes") // Todas las rutas aquí empezarán con /api/dishes
public class DishController {

    @Autowired
    private DishService dishService;

    // Endpoint para OBTENER todos los platos
    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        List<DishDTO> dishes = dishService.findAll();
        return ResponseEntity.ok(dishes); // Devuelve la lista con un estado 200 OK
    }

    // Endpoint para CREAR un nuevo plato
    @PostMapping
    public ResponseEntity<DishDTO> createDish(@RequestBody DishDTO dishDTO) {
        DishDTO createdDish = dishService.save(dishDTO);
        // Devuelve el plato recién creado con un estado 201 CREATED
        return new ResponseEntity<>(createdDish, HttpStatus.CREATED);
    }
}