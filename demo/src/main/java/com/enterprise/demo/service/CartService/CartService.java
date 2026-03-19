package com.enterprise.demo.service.CartService;

import com.enterprise.demo.dto.cartdto.AddToCartRequest;
import com.enterprise.demo.dto.cartdto.CartResponse;
import com.enterprise.demo.dto.cartdto.UpdateCartItemRequest;

public interface CartService {
    CartResponse addToCart(AddToCartRequest request);
    CartResponse getCart();
    CartResponse updateQuantity(UpdateCartItemRequest request);
    void removeCartItem(Long variantId);
    void clearCart();

}
