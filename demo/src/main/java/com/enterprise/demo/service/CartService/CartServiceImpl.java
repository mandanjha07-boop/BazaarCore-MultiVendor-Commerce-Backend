package com.enterprise.demo.service.CartService;

import com.enterprise.demo.dto.cartdto.AddToCartRequest;
import com.enterprise.demo.dto.cartdto.CartResponse;
import com.enterprise.demo.dto.cartdto.UpdateCartItemRequest;
import com.enterprise.demo.entity.Cart;
import com.enterprise.demo.entity.CartItem;
import com.enterprise.demo.entity.Customer;
import com.enterprise.demo.entity.ProductVariant;
import com.enterprise.demo.mapper.CartMapper;
import com.enterprise.demo.repository.CartRepository;
import com.enterprise.demo.repository.ProductVariantRepository;
import com.enterprise.demo.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CustomerService customerService;
    private final ProductVariantRepository productVariantRepository;
    private final CartRepository cartRepository;
    @Override
    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {
        Customer customer = customerService.getLoggedInUser();
        Cart cart = customer.getCart();

        ProductVariant variant = productVariantRepository.
                findById(request.getVariantId()).orElseThrow(() ->
                        new RuntimeException("Variant not found"));
        int requestedQuantity = request.getQuantity();
        Optional<CartItem> existingItem =
                cart.getItems().stream()
                        .filter(i->i.getProductVariant().getId()
                                .equals(variant.getId()))
                        .findFirst();
        if(existingItem.isPresent()){
            CartItem item = existingItem.get();
            int newQnt = item.getQuantity()+requestedQuantity;
            if(variant.getStockQuantity()<newQnt){
                throw new RuntimeException("Insufficient stock");
            }
            item.setQuantity(newQnt);
        }
        else {
            CartItem newItem = CartItem.builder()
                    .productVariant(variant)
                    .cart(cart)
                    .quantity(requestedQuantity)
                    .priceSnapshot(variant.getPrice())
                    .build();
            cart.getItems().add(newItem);
        }
        Cart savedCart = cartRepository.save(cart);
        return CartMapper.toCartResponse(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart() {
        Customer loggedInCustomer = customerService.getLoggedInUser();
        Cart cart =  loggedInCustomer.getCart();
        if(cart==null){
            cart = new Cart();
            cart.setCustomer(loggedInCustomer);
            loggedInCustomer.setCart(cart);
            cartRepository.save(cart);

        }
                return CartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateQuantity(UpdateCartItemRequest request) {

        Customer customer = customerService.getLoggedInUser();
        Cart cart = customer.getCart();

        Long variantId = request.getVariantId();

        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductVariant().getId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not in cart"));

        int requestedQty = request.getQuantity();
        if(requestedQty==0){
            cart.getItems().remove(item);
        }

        if (requestedQty > variant.getStockQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        item.setQuantity(requestedQty);

        Cart savedCart = cartRepository.save(cart);

        return CartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional
    public void removeCartItem(Long variantId) {

        Customer customer = customerService.getLoggedInUser();
        Cart cart = customer.getCart();
        CartItem item = cart.getItems().stream().
                filter(i->i.getProductVariant().getId().
                        equals(variantId)).findFirst().orElseThrow(()->
                        new RuntimeException("Item not present in cart"));
        cart.getItems().remove(item);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart() {
        Customer customer = customerService.getLoggedInUser();
        Cart cart = customer.getCart();
        if(cart == null){
            throw new RuntimeException("Cart not found");
        }

        cart.getItems().clear();

        cartRepository.save(cart);

    }
}
