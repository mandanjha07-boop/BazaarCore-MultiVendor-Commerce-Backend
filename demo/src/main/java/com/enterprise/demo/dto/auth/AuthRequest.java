package com.enterprise.demo.dto.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
