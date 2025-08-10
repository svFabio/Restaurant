package com.restaurante.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily_menus")
@Getter
@Setter
public class DailyMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_date", nullable = false, unique = true)
    private LocalDate menuDate;

    // --- Relaciones ---
    // Un menú diario tiene MUCHOS platos.
    // "cascade = CascadeType.ALL" significa que si borramos un menú,
    // también se borrarán todos los platos asociados a él en esta tabla.
    @OneToMany(mappedBy = "dailyMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyMenuDish> dailyMenuDishes = new ArrayList<>();
}
