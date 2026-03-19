package com.enterprise.demo.service.productvariant;

import com.enterprise.demo.dto.productVariant.ProductVariantRequest;
import com.enterprise.demo.dto.productVariant.ProductVariantResponse;

import java.util.List;

public interface ProductVariantService {
    ProductVariantResponse createProductVariant(ProductVariantRequest request);
    List<ProductVariantResponse> getAllVariants();
    ProductVariantResponse getProductVariantsById(Long id);
    ProductVariantResponse updateProductVariant(Long id,ProductVariantRequest request);
    void deleteProductVariantById(Long id);
    void deleteAllVariants();
}
