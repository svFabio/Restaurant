package com.restaurante.pos.dto;

import com.restaurante.pos.entity.OrderType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private LocalDateTime orderTimestamp;
    private OrderType orderType; // <ahora es del tipo OrderType
    private BigDecimal totalPrice;
    private String userName;
    private List<OrderItemDTO> items;
}