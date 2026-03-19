package com.enterprise.demo.mapper;


import com.enterprise.demo.dto.inventory.InventoryRequest;
import com.enterprise.demo.dto.inventory.InventoryResponse;
import com.enterprise.demo.entity.Inventory;
import com.enterprise.demo.entity.ProductVariant;

public class InventoryMapper {
    public static Inventory toEntity(InventoryRequest request, ProductVariant variant){
        return Inventory.builder()
                .totalStock(request.getTotalStock())
                .reservedStock(0)
                .productVariant(variant)
                .build();

    }

    public static InventoryResponse toDto(Inventory inventory){
        return InventoryResponse.builder()
                .id(inventory.getId())
                .totalStock(inventory.getTotalStock())
                .availableStock(inventory.getAvailableStock())
                .reservedStock(inventory.getReservedStock())
                .variantId(inventory.getProductVariant().getId())
                .build();
    }
}
