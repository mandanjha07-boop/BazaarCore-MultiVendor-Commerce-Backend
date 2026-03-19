package com.enterprise.demo.controller;

import com.enterprise.demo.dto.address.AddressRequest;
import com.enterprise.demo.dto.address.AddressResponse;
import com.enterprise.demo.service.addressService.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressServiceImpl addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse addAddress(
            @RequestBody AddressRequest request
    ) {
        return addressService.addAddress(request);
    }

    @GetMapping
    public List<AddressResponse> getMyAddresses() {
        return addressService.savedAddresses();
    }

    @PutMapping("/{addressId}")
    public AddressResponse updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressRequest request
    ) {
        return addressService.updateAddress(request, addressId);
    }

//    @GetMapping(path = "/{addressId}")
//    public AddressResponse getCustomerSelectedAddress(@PathVariable Long addressId){
//        return AddressMapper.toDto(addressService.getCustomerSelectedAddress(addressId));
//    }

}
