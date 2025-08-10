package com.restaurante.pos.controller;

import com.restaurante.pos.dto.CreateOrderDTO;
import com.restaurante.pos.entity.Order;
import com.restaurante.pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        Order createdOrder = orderService.createOrder(createOrderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
}