package com.enterprise.demo.mapper;


import com.enterprise.demo.dto.customer.CustomerRequestDto;
import com.enterprise.demo.dto.customer.CustomerResponse;
import com.enterprise.demo.entity.Customer;


public class CustomerMapper {
    public static CustomerResponse toDto(Customer customer){
        if(customer==null){
            return null;
        }
        return CustomerResponse.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .role(customer.getRole())
                .build();
    }

    public static Customer toEntity(CustomerRequestDto request) {

        if (request == null) {
            return null;
        }

        return Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .password(request.getPassword())
                .build();
    }
}
