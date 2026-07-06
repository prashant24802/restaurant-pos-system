package com.prashant.restaurantpos.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prashant.restaurantpos.auth.dto.AuthResponse;
import com.prashant.restaurantpos.auth.dto.LoginRequest;
import com.prashant.restaurantpos.auth.dto.RegisterRequest;
import com.prashant.restaurantpos.user.entity.User;
import com.prashant.restaurantpos.user.repository.UserRepository;
import com.prashant.restaurantpos.security.jwt.JwtService;
import com.prashant.restaurantpos.auth.dto.AuthResponse;
import com.prashant.restaurantpos.auth.dto.LoginRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        return userRepository.save(user);
    }
    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

            return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }
}