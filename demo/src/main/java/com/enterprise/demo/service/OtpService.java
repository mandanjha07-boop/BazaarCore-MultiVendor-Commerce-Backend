package com.enterprise.demo.service;

import org.springframework.stereotype.Component;

@Component
public class OtpService {
    public String generateOtp(){
        return String.valueOf((int)(Math.random()*900000)+100000);
    }
}

