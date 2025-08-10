package com.restaurante.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyMenuDishDTO {
    private Long dishId; // El ID del plato del catálogo
    private String dishName;
    private int initialStock;
    private int currentStock;
}