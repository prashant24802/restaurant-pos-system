package com.prashant.restaurantpos.auth.dto;

import com.prashant.restaurantpos.user.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private Role role;
}