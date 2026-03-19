package com.enterprise.demo.repository;


import com.enterprise.demo.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findByProductVariantId(Long variantId);
}
