package com.restaurante.pos.repository;

import com.restaurante.pos.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}