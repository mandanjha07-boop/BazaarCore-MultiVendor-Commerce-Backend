package com.enterprise.demo.mapper;


import com.enterprise.demo.dto.category.CategoryRequest;
import com.enterprise.demo.dto.category.CategoryResponse;
import com.enterprise.demo.entity.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryRequest request,Category parentCategory){
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .isActive(request.getIsActive())
                .parentCategory(parentCategory)
                .build();
    }

    public static CategoryResponse toDto(Category category){
        Long parentId = null;

        if (category.getParentCategory() != null) {
            parentId = category.getParentCategory().getId();
        }
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .isActive(category.getIsActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .parentCategoryId(parentId)
                .build();
    }

}
