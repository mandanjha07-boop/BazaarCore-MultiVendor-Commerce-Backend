package com.enterprise.demo.service.addressService;

import com.enterprise.demo.dto.address.AddressRequest;
import com.enterprise.demo.dto.address.AddressResponse;

import java.util.List;

public interface addressService {
    AddressResponse addAddress(AddressRequest request);
    List<AddressResponse> savedAddresses();
    AddressResponse updateAddress(AddressRequest request,Long addressId);

}
