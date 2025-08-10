package com.restaurante.pos.repository;

import com.restaurante.pos.entity.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;

// Esta interfaz hereda todos los métodos CRUD (Crear, Leer, Actualizar, Borrar)
// para la entidad DishCategory gracias a JpaRepository.
public interface DishCategoryRepository extends JpaRepository<DishCategory, Long> {
}