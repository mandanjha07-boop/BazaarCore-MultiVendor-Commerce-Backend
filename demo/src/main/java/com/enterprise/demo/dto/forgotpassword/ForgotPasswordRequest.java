package com.enterprise.demo.dto.forgotpassword;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String email;
    private String newPassword;
}
