// ESTA ES LA LÍNEA CORRECTA
package com.restaurante.pos.service;

import com.restaurante.pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
// Importaciones necesarias
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.restaurante.pos.entity.User appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario: " + username));

        // La línea corregida: ahora creamos una lista con el rol del usuario.
        // Spring Security requiere que los roles empiecen con "ROLE_".
        return new User(appUser.getUsername(), appUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + appUser.getRole())));
    }
}