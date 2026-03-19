package com.enterprise.demo.dto.product;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder

public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private String brand;

    private String imageUrl;

    private Long categoryId;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
