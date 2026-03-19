package com.enterprise.demo.dto.orderdtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceOrderRequest {
    private Long addressId;
}
