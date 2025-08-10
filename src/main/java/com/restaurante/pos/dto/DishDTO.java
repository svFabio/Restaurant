package com.restaurante.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDTO {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    private Long categoryId; // Usamos el ID para la creación/actualización
    private String categoryName; // Mostramos el nombre para la lectura
}