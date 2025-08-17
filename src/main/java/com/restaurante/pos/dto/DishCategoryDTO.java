package com.restaurante.pos.dto;

import lombok.Data;

@Data
public class DishCategoryDTO {
    private Long id;
    private String name;
    private boolean active;
}