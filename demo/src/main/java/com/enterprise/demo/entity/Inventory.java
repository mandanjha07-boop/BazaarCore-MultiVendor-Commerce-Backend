package com.enterprise.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "inventories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer totalStock;
    private Integer reservedStock;
    //private Integer AvailableStock;
    @OneToOne
    @JoinColumn(name = "variant_id",nullable = false,unique = true)
    private ProductVariant productVariant;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt=LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt=LocalDateTime.now();
    }

    @Transient
    public Integer getAvailableStock(){
        if(totalStock==null || reservedStock==null) return null;
        return totalStock-reservedStock;
    }
}
