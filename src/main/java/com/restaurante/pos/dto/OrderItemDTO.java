package com.restaurante.pos.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    // ID del plato en el menú del día (para crear el pedido)
    private Long dailyMenuDishId;

    // Nombre del plato (para mostrar en el historial)
    private String dishName;
}