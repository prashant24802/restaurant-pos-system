package com.prashant.restaurantpos.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prashant.restaurantpos.auth.dto.AuthResponse;
import com.prashant.restaurantpos.auth.dto.LoginRequest;
import com.prashant.restaurantpos.auth.dto.RegisterRequest;
import com.prashant.restaurantpos.auth.service.AuthService;
import com.prashant.restaurantpos.user.entity.User;
import com.prashant.restaurantpos.auth.dto.LoginRequest;
import com.prashant.restaurantpos.auth.dto.AuthResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}