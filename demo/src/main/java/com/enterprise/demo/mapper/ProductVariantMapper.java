package com.enterprise.demo.mapper;

import com.enterprise.demo.dto.productVariant.ProductVariantRequest;
import com.enterprise.demo.dto.productVariant.ProductVariantResponse;
import com.enterprise.demo.entity.Product;
import com.enterprise.demo.entity.ProductVariant;

public class ProductVariantMapper {
    public static ProductVariant toEntity(ProductVariantRequest request, Product product){
        return ProductVariant.builder()
                .sku(request.getSku())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .attributes(request.getAttributes())
                .product(product)
                .build();
    }

    public static ProductVariantResponse toDto(ProductVariant variant){

        Long productId = variant.getProduct() != null
                ? variant.getProduct().getId()
                : null;

        return ProductVariantResponse.builder()
                .id(variant.getId())
                .sku(variant.getSku())
                .price(variant.getPrice())
                .stockQuantity(variant.getStockQuantity())
                .attributes(variant.getAttributes())
                .productId(productId)
                .createdAt(variant.getCreatedAt())
                .updatedAt(variant.getUpdatedAt())
                .build();
    }
}
