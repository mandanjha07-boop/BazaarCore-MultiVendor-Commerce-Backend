package com.enterprise.demo.dto.cartdto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddToCartRequest {
    private Long variantId;
    private Integer quantity;
}
