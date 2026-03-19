package com.enterprise.demo.dto.orderdtos;

import com.enterprise.demo.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponses {
    private Long orderId;
    private List<OrderItemResponses> items;
    private OrderStatus status;
    private BigDecimal totalSum;
    private LocalDateTime createdAt;

}
