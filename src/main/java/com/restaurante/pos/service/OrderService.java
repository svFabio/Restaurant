package com.restaurante.pos.service;

import com.restaurante.pos.dto.CreateOrderDTO;
import com.restaurante.pos.dto.OrderItemDTO;
import com.restaurante.pos.entity.*;
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

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DailyMenuDishRepository dailyMenuDishRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional // ¡Esta transacción es crucial!
    public Order createOrder(CreateOrderDTO createOrderDTO) {
        // 1. Buscar al usuario que registra el pedido
        User user = userRepository.findById(createOrderDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderTimestamp(LocalDateTime.now());
        order.setOrderType(createOrderDTO.getOrderType());
        order.setTotalPrice(new BigDecimal("15.00")); // Simplificación, por ahora el precio es fijo

        List<OrderItem> orderItems = new ArrayList<>();

        // 2. Procesar cada plato del pedido
        for (OrderItemDTO itemDto : createOrderDTO.getItems()) {
            // 3. Buscar el plato en el menú del día
            DailyMenuDish menuDish = dailyMenuDishRepository.findById(itemDto.getDailyMenuDishId())
                    .orElseThrow(() -> new EntityNotFoundException("Plato del menú no encontrado"));

            // 4. ¡Verificar y descontar el stock!
            if (menuDish.getCurrentStock() <= 0) {
                throw new IllegalStateException("No hay stock disponible para: " + menuDish.getDish().getName());
            }
            menuDish.setCurrentStock(menuDish.getCurrentStock() - 1);
            dailyMenuDishRepository.save(menuDish); // Guardamos el stock actualizado

            // 5. Crear el item del pedido
            OrderItem orderItem = new OrderItem();
            orderItem.setDish(menuDish.getDish());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        // 6. Guardar el pedido completo
        return orderRepository.save(order);
    }
}