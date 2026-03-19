package com.enterprise.demo.service.inventoryService;

import com.enterprise.demo.dto.inventory.InventoryRequest;
import com.enterprise.demo.dto.inventory.InventoryResponse;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest request);
    InventoryResponse getInventoryByVariantId(Long variantId);
    InventoryResponse addStock(Long variantId,Integer stock);
    InventoryResponse reserveStock(Long variantId,Integer stock);
    InventoryResponse releaseStock(Long variantId,Integer stock);
    InventoryResponse deductStock(Long variantId,Integer stock);

}
