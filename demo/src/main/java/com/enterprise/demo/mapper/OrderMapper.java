package com.enterprise.demo.mapper;

import com.enterprise.demo.dto.orderdtos.OrderItemResponses;
import com.enterprise.demo.dto.orderdtos.OrderResponses;
import com.enterprise.demo.entity.Order;
import com.enterprise.demo.entity.OrderItem;

import java.util.List;

public class OrderMapper {
    public static OrderResponses toOrderResponse(Order order){
        List<OrderItemResponses> items = order.getItems()==null?List.of()
                :order.getItems().stream()
                .map(OrderMapper::toOrderItemResponse)
                .toList();
        return OrderResponses.builder()
                .orderId(order.getId())
                .items(items)
                .totalSum(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();

    }

    public static OrderItemResponses toOrderItemResponse(OrderItem orderItem){
        return OrderItemResponses.builder()
                .variantId(orderItem.getVariant().getId())
                .productName(orderItem.getVariant().getProduct().getName())
                .sku(orderItem.getVariant().getSku())
                .attributes(orderItem.getVariant().getAttributes())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPriceSnapshot())
                .lineTotal(orderItem.getLineTotal())
                .build();
    }

}
