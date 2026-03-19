package com.enterprise.demo.controller;

import com.enterprise.demo.dto.cartdto.AddToCartRequest;
import com.enterprise.demo.dto.cartdto.CartResponse;
import com.enterprise.demo.dto.cartdto.UpdateCartItemRequest;
import com.enterprise.demo.service.CartService.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(
            @RequestBody AddToCartRequest request
    ){
        return ResponseEntity.ok(
                cartService.addToCart(request)
        );
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(){
        return ResponseEntity.ok(
                cartService.getCart()
        );
    }

    @PutMapping("/items")
    public ResponseEntity<CartResponse> updateQuantity(
            @RequestBody UpdateCartItemRequest request
    ){
        return ResponseEntity.ok(
                cartService.updateQuantity(request)
        );
    }

    @DeleteMapping("/items/{variantId}")
    public ResponseEntity<String> removeItem(
            @PathVariable Long variantId
    ){
        cartService.removeCartItem(variantId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @DeleteMapping("/items")
    public ResponseEntity<String> clearCart(){
        cartService.clearCart();
        return ResponseEntity.ok("Cart cleared successfully");
    }
}