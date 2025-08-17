package com.restaurante.pos.controller;

import com.restaurante.pos.dto.CreateDailyMenuDTO;
import com.restaurante.pos.dto.DailyMenuDTO;
import com.restaurante.pos.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // Endpoint para CREAR un menú diario
    @PostMapping
    public ResponseEntity<DailyMenuDTO> createDailyMenu(@RequestBody CreateDailyMenuDTO createDto) {
        DailyMenuDTO createdMenu = menuService.createDailyMenu(createDto);
        return new ResponseEntity<>(createdMenu, HttpStatus.CREATED);
    }

    // Endpoint para OBTENER el menú de una fecha específica
    // Se usará así: GET /api/menus/by-date?date=2025-08-11
    @GetMapping("/by-date")
    public ResponseEntity<DailyMenuDTO> getMenuByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<DailyMenuDTO> menuDtoOptional = menuService.getMenuByDate(date);

        if (menuDtoOptional.isPresent()) {
            return ResponseEntity.ok(menuDtoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}