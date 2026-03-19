package com.enterprise.demo.controller;

import com.enterprise.demo.dto.customer.CustomerRequestDto;
import com.enterprise.demo.dto.customer.CustomerResponse;
import com.enterprise.demo.dto.otp.OtpRequest;
import com.enterprise.demo.dto.auth.AuthRequest;
import com.enterprise.demo.dto.auth.AuthResponse;
import com.enterprise.demo.dto.forgotpassword.ForgotPasswordRequest;
import com.enterprise.demo.dto.forgotpassword.ForgotPasswordResponse;
import com.enterprise.demo.service.authService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<CustomerResponse> register(@RequestBody CustomerRequestDto customerRequestDto){
        CustomerResponse response=authService.register(customerRequestDto);
        return ResponseEntity.ok(response);
    }
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request){
      String str=authService.login(request);
      return ResponseEntity.ok(str);
    }
    @PostMapping(path = "/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@RequestBody OtpRequest request){
        AuthResponse response = authService.verifyOtp(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping(path = "/forget-password")
    public ResponseEntity<ForgotPasswordResponse> forgetPassword(@RequestBody ForgotPasswordRequest request){
        return ResponseEntity.ok(authService.forgetPassword(request));
    }
}
