package com.restaurante.pos;

import com.restaurante.pos.entity.DishCategory;
import com.restaurante.pos.entity.User;
import com.restaurante.pos.repository.DishCategoryRepository;
import com.restaurante.pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DishCategoryRepository dishCategoryRepository; // Repositorio de categorías

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // --- Sembrar Usuario Admin ---
        if (userRepository.count() == 0) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("12345"));
            adminUser.setRole("ADMIN");
            userRepository.save(adminUser);
            System.out.println(">>> Usuario 'admin' creado por defecto <<<");
        }

        // --- Sembrar Categorías de Platos ---
        if (dishCategoryRepository.count() == 0) {
            DishCategory sopas = new DishCategory();
            sopas.setName("Sopas");

            DishCategory entradas = new DishCategory();
            entradas.setName("Entradas");

            DishCategory segundos = new DishCategory();
            segundos.setName("Segundos");

            dishCategoryRepository.saveAll(Arrays.asList(sopas, entradas, segundos));
            System.out.println(">>> Categorías de platos creadas por defecto (Sopas, Entradas, Segundos) <<<");
        }
    }
}