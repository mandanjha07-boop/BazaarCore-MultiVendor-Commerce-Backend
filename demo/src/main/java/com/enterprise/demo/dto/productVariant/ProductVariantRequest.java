package com.enterprise.demo.dto.productVariant;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder

public class ProductVariantRequest {
    private String sku;

    private BigDecimal price;

    private Integer stockQuantity;

    private String attributes;

    private Long productId;
}
