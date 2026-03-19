package com.enterprise.demo.service.addressService;

import com.enterprise.demo.dto.address.AddressRequest;
import com.enterprise.demo.dto.address.AddressResponse;
import com.enterprise.demo.entity.Address;
import com.enterprise.demo.entity.Customer;
import com.enterprise.demo.mapper.AddressMapper;
import com.enterprise.demo.repository.AddressRepository;
import com.enterprise.demo.service.customerService.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements addressService{
    private final CustomerService customerService;
    private final AddressRepository addressRepository;

    @Transactional
    public AddressResponse addAddress(AddressRequest request){

        Customer customer = customerService.getLoggedInUser();
        Address address=AddressMapper.toEntity(customer,request);
        boolean hasAddress = addressRepository.existsByCustomerId(customer.getId());
        if(!hasAddress){
           address.setIsDefault(true);
        }
        else if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.clearDefaultForCustomer(customer.getId());
            address.setIsDefault(true);
        }
        return AddressMapper.toDto(addressRepository.save(address));
    }

    public List<AddressResponse> savedAddresses(){
        Customer customer = customerService.getLoggedInUser();
        return addressRepository.findByCustomerId(customer.getId()).stream().map(AddressMapper::toDto).toList();
    }

    @Transactional
    public AddressResponse updateAddress(AddressRequest request,Long addressId) {
        Customer customer = customerService.getLoggedInUser();

        Address existingAddress = addressRepository
                .findById(addressId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Address Not Found with id " + addressId));

        if (!existingAddress.getCustomer().getId().equals(customer.getId())) {
            throw new IllegalArgumentException("You are trying to update another customer's address");
        }

        if (Boolean.TRUE .equals(request.getIsDefault())) {
            addressRepository.clearDefaultForCustomer(customer.getId());
            existingAddress.setIsDefault(true);
        }

        if (request.getAddressType() != null) {
            existingAddress.setAddressType(request.getAddressType());
        }

        if (request.getShippingAddressLine() != null) {
            existingAddress.setShippingAddressLine(request.getShippingAddressLine());
        }

        if (request.getShippingCity() != null) {
            existingAddress.setShippingCity(request.getShippingCity());
        }

        if (request.getShippingPinCode() != null) {
            existingAddress.setShippingPinCode(request.getShippingPinCode());
        }

        if (request.getShippingPhone() != null) {
            existingAddress.setShippingPhone(request.getShippingPhone());
        }

        if (request.getShippingName() != null) {
            existingAddress.setShippingName(request.getShippingName());
        }

        Address saved = addressRepository.save(existingAddress);

        return AddressMapper.toDto(saved);
    }

    public Address getCustomerSelectedAddress(Long addressId){
        Customer customer = customerService.getLoggedInUser();
        Address address = addressRepository
                .findByIdAndCustomerId(addressId, customer.getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));
         return address;
    }
}
