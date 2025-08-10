package com.restaurante.pos.config;

import com.restaurante.pos.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor // Usamos esto para la inyección de dependencias
public class ApplicationConfig {

    private final UserDetailsServiceImpl userDetailsService;

    // ¡AQUÍ ESTÁ LA PIEZA FALTANTE!
    // Le damos a Spring las instrucciones para crear el PasswordEncoder.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Ya no necesitamos el @Bean para UserDetailsService, Spring lo maneja.

    // Bean que es el proveedor de autenticación.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Inyectamos directamente
        authProvider.setPasswordEncoder(passwordEncoder()); // Llamamos al método del bean
        return authProvider;
    }

    // Bean del "gerente de autenticación" que usaremos en nuestro controlador
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}