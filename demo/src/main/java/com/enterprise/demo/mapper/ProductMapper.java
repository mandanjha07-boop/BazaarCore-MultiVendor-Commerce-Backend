package com.enterprise.demo.mapper;

import com.enterprise.demo.dto.product.ProductRequest;
import com.enterprise.demo.dto.product.ProductResponse;
import com.enterprise.demo.dto.profile.UpdateProfileRequest;
import com.enterprise.demo.entity.Category;
import com.enterprise.demo.entity.Product;
import com.enterprise.demo.entity.Profile;

public class ProductMapper {
    public static Product toEntity(ProductRequest request, Category category) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .brand(request.getBrand())
                .category(category)
                .build();
    }

    public static ProductResponse toDto(Product product){
        Long categoryId = product.getCategory() !=null
                ?product.getCategory().getId()
                :null;
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .categoryId(categoryId)
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

}
