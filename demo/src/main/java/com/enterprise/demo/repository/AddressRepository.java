package com.enterprise.demo.repository;

import com.enterprise.demo.dto.address.AddressResponse;
import com.enterprise.demo.entity.Address;
import com.enterprise.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    @Modifying
    @Query("""
        UPDATE Address a
        SET a.isDefault = false
        WHERE a.customer.id = :customerId
       """)
    int clearDefaultForCustomer(Long id);
    List<Address> findByCustomerId(Long customerId);
    boolean existsByCustomerId(Long customerId);

    Optional<Address> findByIdAndCustomerId(Long addressId, Long customerId);
}

