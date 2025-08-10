package com.restaurante.pos.controller;

import com.restaurante.pos.dto.LoginRequest;
import com.restaurante.pos.dto.LoginResponse;
import com.restaurante.pos.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Todas las rutas en este controlador empezarán con /api/auth
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    // Endpoint para el login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // 1. Autenticar al usuario con el AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. Si la autenticación es exitosa, obtener los detalles del usuario
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Generar el token JWT
        String token = jwtService.generateToken(userDetails);

        // 4. Devolver el token en la respuesta
        return ResponseEntity.ok(new LoginResponse(token));
    }
}