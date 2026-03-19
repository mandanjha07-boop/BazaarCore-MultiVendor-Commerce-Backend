package com.enterprise.demo.controller;

import com.enterprise.demo.dto.inventory.InventoryRequest;
import com.enterprise.demo.dto.inventory.InventoryResponse;
import com.enterprise.demo.service.inventoryService.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(
            @RequestBody InventoryRequest request) {

        return ResponseEntity.ok(inventoryService.createInventory(request));
    }

    @GetMapping("/{variantId}")
    public ResponseEntity<InventoryResponse> getInventoryByVariantId(
            @PathVariable Long variantId) {

        return ResponseEntity.ok(inventoryService.getInventoryByVariantId(variantId));
    }

    @PostMapping("/{variantId}/add")
    public ResponseEntity<InventoryResponse> addStock(
            @PathVariable Long variantId,
            @RequestParam Integer stock) {

        return ResponseEntity.ok(inventoryService.addStock(variantId, stock));
    }

    @PostMapping("/{variantId}/reserve")
    public ResponseEntity<InventoryResponse> reserveStock(
            @PathVariable Long variantId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(inventoryService.reserveStock(variantId, quantity));
    }

    @PostMapping("/{variantId}/release")
    public ResponseEntity<InventoryResponse> releaseStock(
            @PathVariable Long variantId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(inventoryService.releaseStock(variantId, quantity));
    }

    @PostMapping("/{variantId}/deduct")
    public ResponseEntity<InventoryResponse> deductStock(
            @PathVariable Long variantId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(inventoryService.deductStock(variantId, quantity));
    }
}
