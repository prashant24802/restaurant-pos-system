package com.prashant.restaurantpos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.prashant.restaurantpos.user.entity.Role;
import com.prashant.restaurantpos.user.entity.User;
import com.prashant.restaurantpos.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("prashant@gmail.com")) {

            User admin = User.builder()
                    .name("Prashant")
                    .email("prashant@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);

            System.out.println("✅ Admin user created successfully!");
        }
    }
}