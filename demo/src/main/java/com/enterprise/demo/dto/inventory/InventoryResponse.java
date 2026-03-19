package com.enterprise.demo.dto.inventory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class InventoryResponse {
    private Long id;

    private Long variantId;

    private Integer totalStock;

    private Integer reservedStock;

    private Integer availableStock;
}
