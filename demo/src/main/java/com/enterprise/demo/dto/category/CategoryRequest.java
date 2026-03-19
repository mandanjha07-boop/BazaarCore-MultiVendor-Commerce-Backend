package com.enterprise.demo.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CategoryRequest {
    private String name;
    private String description;
    private String imageUrl;
    private Boolean isActive;
    private Long parentCategoryId;
}
