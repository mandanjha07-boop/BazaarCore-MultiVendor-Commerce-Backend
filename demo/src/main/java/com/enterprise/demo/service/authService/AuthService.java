package com.enterprise.demo.service.authService;

import com.enterprise.demo.dto.customer.CustomerRequestDto;
import com.enterprise.demo.dto.customer.CustomerResponse;
import com.enterprise.demo.dto.otp.OtpRequest;
import com.enterprise.demo.dto.auth.AuthRequest;
import com.enterprise.demo.dto.auth.AuthResponse;
import com.enterprise.demo.dto.forgotpassword.ForgotPasswordRequest;
import com.enterprise.demo.dto.forgotpassword.ForgotPasswordResponse;

public interface AuthService {

    CustomerResponse register(CustomerRequestDto customerRequestDto);
    String login(AuthRequest request);
    public AuthResponse verifyOtp(OtpRequest request);
    public ForgotPasswordResponse forgetPassword(ForgotPasswordRequest request);
}
