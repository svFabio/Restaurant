package com.restaurante.pos.repository;

import com.restaurante.pos.entity.DailyMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyMenuRepository extends JpaRepository<DailyMenu, Long> {

    // Spring Data JPA creará automáticamente la consulta para este método
    // basándose en el nombre. Buscará un DailyMenu por su campo 'menuDate'.
    Optional<DailyMenu> findByMenuDate(LocalDate menuDate);
}