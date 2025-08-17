package com.restaurante.pos.dto;

import com.restaurante.pos.entity.OrderType;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderDTO {
    private OrderType orderType;
    private List<Long> dailyMenuDishIds; // Una lista con los IDs de los platos seleccionados
}