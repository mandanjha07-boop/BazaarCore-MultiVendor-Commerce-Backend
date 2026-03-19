package com.enterprise.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    public void sendOtp(String email,String otp){
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(otp);
        msg.setText("Your Otp is :"+otp);

        javaMailSender.send(msg);
    }
}
