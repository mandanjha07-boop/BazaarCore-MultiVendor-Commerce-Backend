package com.enterprise.demo.controller;

import com.enterprise.demo.dto.orderdtos.OrderResponses;
import com.enterprise.demo.dto.orderdtos.PlaceOrderRequest;
import com.enterprise.demo.service.orderService.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponses> placeOrder(
            @RequestBody PlaceOrderRequest request
    ){
        return ResponseEntity.ok(
                orderService.placeOrder(request)
        );
    }

    public ResponseEntity<List<OrderResponses>> getMyOrders(){
        return ResponseEntity.ok(
                orderService.getMyOrders()
        );
    }

    @GetMapping
    public ResponseEntity<OrderResponses> getOrderById(
            @PathVariable Long orderId
    ){
        return ResponseEntity.ok(
                orderService.getOrderById(orderId)
        );
    }
    @Transactional
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponses> cancelOrder(
            @PathVariable Long orderId
    ){
        return ResponseEntity.ok(
                orderService.cancelOrder(orderId)
        );
    }
}