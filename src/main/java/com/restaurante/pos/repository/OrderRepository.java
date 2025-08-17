package com.restaurante.pos.repository;

import com.restaurante.pos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderTimestampBetween(LocalDateTime start, LocalDateTime end);

}