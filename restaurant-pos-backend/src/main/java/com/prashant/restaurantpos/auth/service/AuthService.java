package com.prashant.restaurantpos.auth.service;

import com.prashant.restaurantpos.auth.dto.AuthResponse;
import com.prashant.restaurantpos.auth.dto.LoginRequest;
import com.prashant.restaurantpos.auth.dto.RegisterRequest;
import com.prashant.restaurantpos.user.entity.User;

public interface AuthService {

    User register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}