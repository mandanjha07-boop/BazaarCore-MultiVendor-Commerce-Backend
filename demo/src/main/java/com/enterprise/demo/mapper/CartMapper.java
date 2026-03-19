package com.enterprise.demo.mapper;

import com.enterprise.demo.dto.cartdto.CartItemResponse;
import com.enterprise.demo.dto.cartdto.CartResponse;
import com.enterprise.demo.entity.Cart;
import com.enterprise.demo.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class CartMapper {
    public static CartResponse toCartResponse(Cart cart){
        List<CartItemResponse> itemResponses = cart.getItems()==null?List.of():
                cart.getItems().stream().map(CartMapper::toCartItemResponse).toList();
        BigDecimal cartTotal =
                itemResponses.stream()
                        .map(CartItemResponse::getLineTotal)
                        .reduce(BigDecimal.ZERO,BigDecimal::add);

        return CartResponse.builder()
                .cartId(cart.getId())
                .items(itemResponses)
                .cartTotal(cartTotal)
                .build();
    }
    public static CartItemResponse toCartItemResponse(CartItem cartItem){
        BigDecimal price = cartItem.getPriceSnapshot();
        BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        return CartItemResponse.builder()
                .sku(cartItem.getProductVariant().getSku())
                .variantId(cartItem.getProductVariant().getId())
                .attributes(cartItem.getProductVariant().getAttributes())
                .quantity(cartItem.getQuantity())
                .productName(cartItem.getProductVariant().getProduct().getName())
                .price(price)
                .lineTotal(lineTotal)
                .build();
    }
}
