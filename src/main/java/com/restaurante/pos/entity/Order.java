package com.restaurante.pos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders") // "order" es una palabra reservada en SQL, por eso usamos "orders"
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderTimestamp;

    @Column(nullable = false)
    private String orderType; // "MENU_COMPLETO" o "SOLO_SEGUNDO"

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference //optmiz tamanio
    private List<OrderItem> orderItems = new ArrayList<>();
}