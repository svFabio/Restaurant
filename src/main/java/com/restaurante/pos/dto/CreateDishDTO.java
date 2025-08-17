package com.restaurante.pos.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateDishDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private boolean active = true;
    private Long categoryId; // Recibimos el ID de la categoría
}