package com.enterprise.demo.dto.product;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String brand;

   private Long categoryId;


}
