package com.restaurante.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "dish_categories")
@Getter
@Setter
public class DishCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // --- ESTE ES EL CAMPO QUE FALTABA ---
    @Column(nullable = false)
    private boolean active = true; // Por defecto, las categorías son activas

    // --- Relaciones ---
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Evita que se serialicen los platos y se creen bucles
    private List<Dish> dishes;
}