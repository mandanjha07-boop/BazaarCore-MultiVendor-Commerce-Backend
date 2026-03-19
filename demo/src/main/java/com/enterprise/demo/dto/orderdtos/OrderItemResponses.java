package com.enterprise.demo.dto.orderdtos;

import com.enterprise.demo.entity.ProductVariant;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponses {
    private Long variantId;

    private String sku;

    private String productName;

    private String attributes;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal lineTotal;
}
