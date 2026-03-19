package com.enterprise.demo.repository;

import com.enterprise.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
      Customer findByEmail(String email);
}
