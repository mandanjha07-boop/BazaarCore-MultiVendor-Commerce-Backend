package com.enterprise.demo.dto.cartdto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder

public class CartItemResponse {
    private String sku;
    private Long variantId;
    private String productName;
    private Integer quantity;
    private String attributes;
    private BigDecimal price;
    private BigDecimal lineTotal;

}
