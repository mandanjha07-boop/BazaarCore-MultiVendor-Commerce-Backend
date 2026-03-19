package com.enterprise.demo.dto.productVariant;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data

public class ProductVariantResponse {

    private Long id;

    private String sku;

    private BigDecimal price;

    private Integer stockQuantity;

    private String attributes;

    private Long productId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
