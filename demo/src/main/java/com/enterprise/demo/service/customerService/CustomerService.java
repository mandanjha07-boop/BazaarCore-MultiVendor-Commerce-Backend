package com.enterprise.demo.service.customerService;

import com.enterprise.demo.entity.Customer;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    public Customer getLoggedInUser();
}
