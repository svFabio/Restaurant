package com.restaurante.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // Crea un constructor con todos los argumentos
public class LoginResponse {
    private String jwt;
}