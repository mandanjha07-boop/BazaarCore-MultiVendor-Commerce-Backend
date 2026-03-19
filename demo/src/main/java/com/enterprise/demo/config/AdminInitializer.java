package com.enterprise.demo.config;

import com.enterprise.demo.entity.Customer;
import com.enterprise.demo.entity.Role;
import com.enterprise.demo.repository.CustomerRepository;
import com.enterprise.demo.service.mediaService.S3MediaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor

public class AdminInitializer {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.name}")
    private String name;

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.password}")
    private String password;
    @Value("${app.admin.phone}")
    private String phone;

    @Bean
    public CommandLineRunner createAdmin(){
        return args -> {
          Customer existingAdmin = customerRepository.findByEmail(email);
          if(existingAdmin==null){
              Customer Admin= new Customer();
              Admin.setName(name);
              Admin.setEmail(email);
              Admin.setPassword(passwordEncoder.encode(password));
              Admin.setName(phone);
              Admin.setRole(Role.ADMIN);
              customerRepository.save(Admin);
              System.out.println("Admin user created successfully");
          }else {
              System.out.println("Admin already exists");
          }

        };
    }

    @Bean
    CommandLineRunner runner(S3MediaServiceImpl mediaService) {

        return args -> {
            mediaService.testConnection();
        };
    }

}
