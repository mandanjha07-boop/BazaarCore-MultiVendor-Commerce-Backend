package com.enterprise.demo.dto.category;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryResponse {
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Boolean isActive;

    private Long parentCategoryId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
