package com.restaurante.pos;

import com.restaurante.pos.entity.User;
import com.restaurante.pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificamos si ya existe algún usuario para no crearlo de nuevo
        if (userRepository.count() == 0) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            // ¡Importante! Encriptamos la contraseña antes de guardarla
            adminUser.setPassword(passwordEncoder.encode("12345"));
            adminUser.setRole("ADMIN");

            userRepository.save(adminUser);
            System.out.println("> Usuario 'admin' creado por defecto");
        }
    }
}