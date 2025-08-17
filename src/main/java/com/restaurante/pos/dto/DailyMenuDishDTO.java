package com.restaurante.pos.dto;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyMenuDishDTO {
    private long id;
    private Long dishId; // El ID del plato del catálogo
    private String dishName;
    private int initialStock;
    private int currentStock;
    private BigDecimal price;
    private String categoryName;
}