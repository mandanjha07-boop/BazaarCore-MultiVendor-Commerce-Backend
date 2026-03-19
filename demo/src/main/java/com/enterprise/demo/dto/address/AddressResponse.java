package com.enterprise.demo.dto.address;

import com.enterprise.demo.entity.AddressType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private Long id;
    private String shippingName;
    private String shippingAddressLine;
    private String shippingPhone;
    private String shippingCity;
    private String shippingPinCode;
    private AddressType addressType;
    private boolean isDefault;
}
