package com.enterprise.demo.service.customerService;

import com.enterprise.demo.entity.Customer;
import com.enterprise.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
private final PasswordEncoder passwordEncoder;
private final CustomerRepository customerRepository;

    @Override
    public Customer registerCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);

    }

    public Customer getLoggedInUser(){
        String email= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return customerRepository.findByEmail(email);
    }
}
