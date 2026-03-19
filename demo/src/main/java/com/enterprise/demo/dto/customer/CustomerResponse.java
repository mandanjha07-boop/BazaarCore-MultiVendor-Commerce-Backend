package com.enterprise.demo.dto.customer;

import com.enterprise.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

    private String name;
    private String email;
    private String phoneNumber;
    private Role role;
}
