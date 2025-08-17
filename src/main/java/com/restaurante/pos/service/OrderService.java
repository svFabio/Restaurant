// Reemplaza el contenido de este archivo.
// La línea "order.setOrderType(placeOrderDTO.getOrderType());"
// ya no dará error.

package com.restaurante.pos.service;

import com.restaurante.pos.dto.OrderDTO;
import com.restaurante.pos.dto.OrderItemDTO;
import com.restaurante.pos.dto.PlaceOrderDTO;
import com.restaurante.pos.entity.*;
import com.restaurante.pos.repository.AppSettingsRepository;
import com.restaurante.pos.repository.DailyMenuDishRepository;
import com.restaurante.pos.repository.OrderRepository;
import com.restaurante.pos.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private DailyMenuDishRepository dailyMenuDishRepository;
    @Autowired private AppSettingsRepository appSettingsRepository;

    @Transactional
    public OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO) {
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderTimestamp(LocalDateTime.now());
        order.setOrderType(placeOrderDTO.getOrderType()); // <-- ESTA LÍNEA AHORA FUNCIONARÁ

        List<OrderItem> orderItems = new ArrayList<>();

        for (Long menuDishId : placeOrderDTO.getDailyMenuDishIds()) {
            DailyMenuDish menuDish = dailyMenuDishRepository.findById(menuDishId)
                    .orElseThrow(() -> new EntityNotFoundException("Plato del menú no encontrado con id: " + menuDishId));

            if (menuDish.getCurrentStock() <= 0) {
                throw new IllegalStateException("No hay stock para: " + menuDish.getDish().getName());
            }
            menuDish.setCurrentStock(menuDish.getCurrentStock() - 1);
            dailyMenuDishRepository.save(menuDish);

            OrderItem orderItem = new OrderItem();
            orderItem.setDish(menuDish.getDish());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        BigDecimal totalPrice;
        if (placeOrderDTO.getOrderType() == OrderType.MENU_COMPLETO) {
            totalPrice = new BigDecimal(appSettingsRepository.findById("precio_menu_completo")
                    .orElseThrow(() -> new IllegalStateException("Precio de menú completo no configurado")).getValue());
        } else {
            totalPrice = new BigDecimal(appSettingsRepository.findById("precio_solo_segundo")
                    .orElseThrow(() -> new IllegalStateException("Precio de solo segundo no configurado")).getValue());
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderTimestamp(order.getOrderTimestamp());
        dto.setOrderType(order.getOrderType());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setUserName(order.getUser().getUsername());

        List<OrderItemDTO> itemDTOs = order.getOrderItems().stream().map(item -> {
            OrderItemDTO itemDto = new OrderItemDTO();
            itemDto.setDishName(item.getDish().getName());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(itemDTOs);
        return dto;
    }
}