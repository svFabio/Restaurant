package com.restaurante.pos.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CreateOrderDTO {
    private String orderType; // "MENU_COMPLETO" o "SOLO_SEGUNDO"
    private List<OrderItemDTO> items;
    private Long userId; // El ID del usuario que toma el pedido
}
