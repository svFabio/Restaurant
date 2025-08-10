package com.restaurante.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dishes")
@Getter
@Setter
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Ej: "Lomo Saltado"

    private String description; // Ej: "Trozos de carne salteados con cebolla y tomate"

    @Column(name = "is_active")
    private boolean isActive = true; // Para poder "desactivar" un plato en lugar de borrarlo

    // --- Relaciones ---
    @ManyToOne // Muchos platos pertenecen a UNA categoría
    @JoinColumn(name = "category_id", nullable = false) // La columna que une las tablas
    private DishCategory category;
}