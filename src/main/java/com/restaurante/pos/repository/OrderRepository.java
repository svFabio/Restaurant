package com.restaurante.pos.repository;

import com.restaurante.pos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}