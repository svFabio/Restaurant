package com.restaurante.pos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    // El ID del plato en el menú del día (daily_menu_dishes)
    private Long dailyMenuDishId;
}