package com.enterprise.demo.controller;

import com.enterprise.demo.dto.productVariant.ProductVariantRequest;
import com.enterprise.demo.dto.productVariant.ProductVariantResponse;
import com.enterprise.demo.service.productvariant.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/variants")
@RequiredArgsConstructor
public class ProductVariantController {


    private final ProductVariantService productVariantService;

    @PostMapping
    public ResponseEntity<ProductVariantResponse> createVariant(
            @RequestBody ProductVariantRequest request) {

        return ResponseEntity.ok(productVariantService.createProductVariant(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductVariantResponse>> getAllVariants() {

        return ResponseEntity.ok(productVariantService.getAllVariants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantResponse> getVariantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(productVariantService.getProductVariantsById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductVariantResponse> updateVariant(
            @PathVariable Long id,
            @RequestBody ProductVariantRequest request) {

        return ResponseEntity.ok(productVariantService.updateProductVariant(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariantById(
            @PathVariable Long id) {

        productVariantService.deleteProductVariantById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllVariants() {

        productVariantService.deleteAllVariants();
        return ResponseEntity.noContent().build();
    }
}
