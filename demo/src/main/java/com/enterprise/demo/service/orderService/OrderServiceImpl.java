package com.enterprise.demo.service.orderService;

import com.enterprise.demo.dto.orderdtos.OrderResponses;
import com.enterprise.demo.dto.orderdtos.PlaceOrderRequest;
import com.enterprise.demo.entity.*;
import com.enterprise.demo.mapper.OrderMapper;
import com.enterprise.demo.repository.CartRepository;
import com.enterprise.demo.repository.OrderRepository;
import com.enterprise.demo.repository.ProductVariantRepository;
import com.enterprise.demo.service.addressService.AddressServiceImpl;
import com.enterprise.demo.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CustomerService customerService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AddressServiceImpl addressService;

    @Transactional
    @Override
    public OrderResponses placeOrder(@RequestBody PlaceOrderRequest request) {
        Customer customer = customerService.getLoggedInUser();
        Cart cart = customer.getCart();
        Address address = addressService.getCustomerSelectedAddress(request.getAddressId());
        Order order = Order.builder()
                .customer(customer)
                .shippingName(address.getShippingName())
                .shippingCity(address.getShippingCity())
                .shippingPincode(address.getShippingPinCode())
                .shippingPhone(address.getShippingPhone())
                .shippingAddressLine(address.getShippingAddressLine())
                .build();
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount=BigDecimal.ZERO;

        for(CartItem cartItem : cart.getItems()){
            ProductVariant variant = cartItem.getProductVariant();
            BigDecimal lineTotal = cartItem.getPriceSnapshot()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            if(cartItem.getQuantity()> variant.getStockQuantity()){
                throw new RuntimeException("This item is currently Out Of Stock"+variant.getSku());
            }
            OrderItem orderItem = OrderItem.builder()
                    .variant(variant)
                    .quantity(cartItem.getQuantity())
                    .order(order)
                    .priceSnapshot(cartItem.getPriceSnapshot())
                    .lineTotal(lineTotal)
                    .build();
            orderItems.add(orderItem);
            totalAmount=totalAmount.add(lineTotal);
            variant.setStockQuantity(variant.getStockQuantity()-cartItem.getQuantity());
        }
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();

        cartRepository.save(cart);
        return OrderMapper.toOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponses> getMyOrders() {
        Customer customer = customerService.getLoggedInUser();
        Long customerID = customer.getId();
        return orderRepository
                .findByCustomerId(customer.getId())
                .stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponses getOrderById(Long id){

        Customer customer = customerService.getLoggedInUser();

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if(!order.getCustomer().getId().equals(customer.getId())){
            throw new RuntimeException("Unauthorized access to order");
        }

        return OrderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponses cancelOrder(Long orderId) {

        Customer customer = customerService.getLoggedInUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException
                        ("Order not Found with this id"+orderId));

        if(!customer.getId().equals(order.getCustomer().getId())){
            throw new RuntimeException("Unauthorized access");
        }

        if(order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.DELIVERED){
            throw new RuntimeException("Order cannot be cancelled");
        }

        if(order.getStatus() == OrderStatus.CANCELLED){
            throw new RuntimeException("Order already cancelled");
        }

        for(OrderItem item : order.getItems()){
            ProductVariant variant  = item.getVariant();
            variant.setStockQuantity(variant.getStockQuantity()+ item.getQuantity());
        }
            order.setStatus(OrderStatus.CANCELLED);


        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toOrderResponse(savedOrder);



    }


}
