package com.restaurante.pos.controller;

import com.restaurante.pos.dto.CreateOrderDTO;
import com.restaurante.pos.dto.OrderDTO; // <-- Ahora sí encontrará esta clase
import com.restaurante.pos.entity.Order;
import com.restaurante.pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.restaurante.pos.dto.PlaceOrderDTO;
import java.util.List; // <-- Importante

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO) {
        OrderDTO createdOrderDto = orderService.placeOrder(placeOrderDTO);
        return new ResponseEntity<>(createdOrderDto, HttpStatus.CREATED);
    }

    // --- MÉTODO CORREGIDO PARA OBTENER TODOS LOS PEDIDOS ---
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}