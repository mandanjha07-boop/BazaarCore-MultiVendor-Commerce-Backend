package com.enterprise.demo.mapper;

import com.enterprise.demo.dto.address.AddressRequest;
import com.enterprise.demo.dto.address.AddressResponse;
import com.enterprise.demo.entity.Address;
import com.enterprise.demo.entity.Customer;

public class AddressMapper {
    public static Address toEntity(Customer customer, AddressRequest request){
        return Address.builder()
                .addressType(request.getAddressType())
                .shippingName(request.getShippingName())
                .shippingAddressLine(request.getShippingAddressLine())
                .shippingPhone(request.getShippingPhone())
                .shippingPinCode(request.getShippingPinCode())
                .shippingCity(request.getShippingCity())
                .isDefault(request.getIsDefault())
                .customer(customer)
                .build();
    }

    public static AddressResponse toDto(Address address){
        return AddressResponse.builder()
                .id(address.getId())
                .addressType(address.getAddressType())
                .shippingPinCode(address.getShippingPinCode())
                .shippingName(address.getShippingName())
                .shippingAddressLine(address.getShippingAddressLine())
                .shippingCity(address.getShippingCity())
                .shippingPhone(address.getShippingPhone())
                .isDefault(address.getIsDefault())
                .build();

    }
}
