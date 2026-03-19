package com.enterprise.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.action.internal.OrphanRemovalAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String shippingName;
    @Column(nullable = false)
    private String shippingPhone;
    @Column(nullable = false)
    private String shippingAddressLine;
    @Column(nullable = false)
    private String shippingCity;
    @Column(nullable = false)
    private String shippingPinCode;
    @ManyToOne
    @JoinColumn(name = "Customer_Address",nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    private Boolean isDefault;
}
