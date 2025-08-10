package com.restaurante.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "daily_menu_dishes")
@Getter
@Setter
public class DailyMenuDish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "initial_stock", nullable = false)
    private int initialStock;

    @Column(name = "current_stock", nullable = false)
    private int currentStock;

    // --- Relaciones ---
    @ManyToOne // MUCHOS "platos de menú" pertenecen a UN menú diario
    @JoinColumn(name = "daily_menu_id", nullable = false)
    private DailyMenu dailyMenu;

    @ManyToOne // MUCHOS "platos de menú" pueden apuntar al MISMO plato del catálogo
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;
}
