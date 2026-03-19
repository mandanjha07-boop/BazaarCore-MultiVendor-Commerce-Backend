package com.enterprise.demo.service.orderService;

import com.enterprise.demo.dto.orderdtos.OrderResponses;
import com.enterprise.demo.dto.orderdtos.PlaceOrderRequest;

import java.util.List;

public interface OrderService {
    OrderResponses placeOrder(PlaceOrderRequest request);
    List<OrderResponses> getMyOrders();
    OrderResponses getOrderById(Long id);
    OrderResponses  cancelOrder(Long orderId);
}
