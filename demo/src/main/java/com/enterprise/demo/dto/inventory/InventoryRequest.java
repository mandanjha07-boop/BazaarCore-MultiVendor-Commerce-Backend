package com.enterprise.demo.dto.inventory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class InventoryRequest {
    private Integer totalStock;
    private Long productVariantId;
}
