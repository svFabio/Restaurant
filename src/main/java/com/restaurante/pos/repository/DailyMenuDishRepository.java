package com.restaurante.pos.repository;

import com.restaurante.pos.entity.DailyMenuDish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyMenuDishRepository extends JpaRepository<DailyMenuDish, Long> {
}