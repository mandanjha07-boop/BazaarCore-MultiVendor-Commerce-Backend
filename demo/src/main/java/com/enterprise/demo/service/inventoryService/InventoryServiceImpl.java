package com.enterprise.demo.service.inventoryService;

import com.enterprise.demo.dto.inventory.InventoryRequest;
import com.enterprise.demo.dto.inventory.InventoryResponse;
import com.enterprise.demo.entity.Inventory;
import com.enterprise.demo.entity.ProductVariant;
import com.enterprise.demo.mapper.InventoryMapper;
import com.enterprise.demo.repository.InventoryRepository;
import com.enterprise.demo.repository.ProductVariantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private final ProductVariantRepository productVariantRepository;
    private final InventoryRepository inventoryRepository;
    @Override
    public InventoryResponse createInventory(InventoryRequest request) {
        ProductVariant variant = productVariantRepository.
                findById(request.getProductVariantId()).orElseThrow();
        Inventory inventory = InventoryMapper.toEntity(request,variant);
        return InventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse getInventoryByVariantId(Long variantId) {
        return InventoryMapper.toDto(inventoryRepository.
                findByProductVariantId(variantId).orElseThrow(()->
                        new EntityNotFoundException
                                ("Inventory not found with this variantId")));
    }

    @Override
    public InventoryResponse addStock(Long variantId, Integer stock) {
        Inventory inventory= inventoryRepository.
                findByProductVariantId(variantId).orElseThrow(()->
                        new EntityNotFoundException
                                ("Inventory not found with this variantId"));
        inventory.setTotalStock(inventory.getTotalStock()+stock);
        inventoryRepository.save(inventory);
        return InventoryMapper.toDto(inventory);
    }
// When User Add Item into The Cart Then it will get deducted from Available Stock
    @Override
    public InventoryResponse reserveStock(Long variantId, Integer quantity) {
        Inventory inventory= inventoryRepository.
                findByProductVariantId(variantId).orElseThrow(()->
                        new EntityNotFoundException
                                ("Inventory not found with this variantId"));
        if(inventory.getAvailableStock()>= quantity){
            inventory.setReservedStock(inventory.getReservedStock()+quantity);

        }else {
            throw new RuntimeException("This Variant is outOfStock this tym Kindly Check Out other Variant");
        }
        inventoryRepository.save(inventory);
        return InventoryMapper.toDto(inventory);
    }
// When User did not placed the order OR cart Got Expired
    @Override
    public InventoryResponse releaseStock(Long variantId, Integer quantity) {

        Inventory inventory= inventoryRepository.
                findByProductVariantId(variantId).orElseThrow(()->
                        new EntityNotFoundException
                                ("Inventory not found with this variantId"));

        if(inventory.getReservedStock() < quantity){
            throw new RuntimeException("Invalid release quantity");
        }

            inventory.setReservedStock(inventory.getReservedStock()-quantity);
        inventoryRepository.save(inventory);

        return InventoryMapper.toDto(inventory);
    }
//When Order got Placed
    @Override
    public InventoryResponse deductStock(Long variantId, Integer quantity) {
        Inventory inventory= inventoryRepository.
                findByProductVariantId(variantId).orElseThrow(()->
                        new EntityNotFoundException
                                ("Inventory not found with this variantId"));
        if(inventory.getReservedStock() < quantity){
            throw new RuntimeException("Invalid deduction quantity");
        }

        inventory.setReservedStock(inventory.getReservedStock()-quantity);
        inventory.setTotalStock(inventory.getTotalStock()-quantity);
        inventoryRepository.save(inventory);
        return InventoryMapper.toDto(inventory);
    }
}
