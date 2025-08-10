package com.restaurante.pos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Creamos el Bean del encriptador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Creamos el Bean de la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos la protección CSRF porque usaremos tokens JWT
                .csrf(csrf -> csrf.disable())

                // Definimos las reglas de autorización para las peticiones HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permitimos el acceso público a nuestro futuro endpoint de login
                        .requestMatchers("/api/auth/login").permitAll()
                        // Cualquier otra petición requerirá autenticación
                        .anyRequest().authenticated()
                )

                // Configuramos la gestión de sesiones para que sea sin estado (STATELESS)
                // Esto es clave para una API REST con JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}