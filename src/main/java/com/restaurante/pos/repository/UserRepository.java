package com.restaurante.pos.repository;

import com.restaurante.pos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA entenderá automáticamente que este método
    // debe buscar en la tabla 'users' un usuario por su columna 'username'.
    Optional<User> findByUsername(String username);
}