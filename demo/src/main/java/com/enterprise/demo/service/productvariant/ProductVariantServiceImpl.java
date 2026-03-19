package com.enterprise.demo.service.productvariant;

import com.enterprise.demo.dto.product.ProductRequest;
import com.enterprise.demo.dto.product.ProductResponse;
import com.enterprise.demo.dto.productVariant.ProductVariantRequest;
import com.enterprise.demo.dto.productVariant.ProductVariantResponse;
import com.enterprise.demo.entity.Product;
import com.enterprise.demo.entity.ProductVariant;
import com.enterprise.demo.mapper.ProductVariantMapper;
import com.enterprise.demo.repository.ProductRepository;
import com.enterprise.demo.repository.ProductVariantRepository;
import com.enterprise.demo.service.productService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {
private final ProductRepository productRepository;
private final ProductVariantRepository productVariantRepository;
    @Override
    public ProductVariantResponse createProductVariant(ProductVariantRequest request) {
        Product product = productRepository.findById
                (request.getProductId()).orElseThrow();

        ProductVariant productVariant = ProductVariantMapper.toEntity(request,product);
        return ProductVariantMapper.toDto(productVariantRepository.save(productVariant));
    }

    @Override
    public List<ProductVariantResponse> getAllVariants() {
        List<ProductVariantResponse> productVariantResponseList =
                productVariantRepository.findAll().stream().
                        map(ProductVariantMapper::toDto).toList();
        return productVariantResponseList;
    }

    @Override
    public ProductVariantResponse getProductVariantsById(Long id) {
        ProductVariant productVariant  = productVariantRepository.findById(id).
                orElseThrow(()->new RuntimeException("ProductVariant Not Found with this id"));

        return ProductVariantMapper.toDto(productVariant);
    }

    @Override
    public ProductVariantResponse updateProductVariant(Long id, ProductVariantRequest request) {
        ProductVariant existingproductVariant = productVariantRepository.findById(id).
                orElseThrow(()->new RuntimeException("ProductVariant Not Found with this id"));

        Product product = null;

        if(request.getProductId() != null){
            product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }
        if(request.getSku()!=null){
            existingproductVariant.setSku(request.getSku());
        }
        if(request.getPrice()!=null){
            existingproductVariant.setPrice(request.getPrice());
        }

        if(request.getAttributes()!=null){
            existingproductVariant.setAttributes(request.getAttributes());
        }
        if(request.getStockQuantity()!=null){
            existingproductVariant.setStockQuantity(request.getStockQuantity());
        }
        if(request.getProductId()!=null){
            existingproductVariant.setProduct(product);
        }

        productVariantRepository.save(existingproductVariant);
        return ProductVariantMapper.toDto(existingproductVariant);
    }

    @Override
    public void deleteProductVariantById(Long id) {
        ProductVariant productVariant  = productVariantRepository.findById(id).
                orElseThrow(()->new RuntimeException("ProductVariant Not Found with this id"));
         productVariantRepository.delete(productVariant);

    }

    @Override
    public void deleteAllVariants() {
       productVariantRepository.deleteAll();
    }
}
