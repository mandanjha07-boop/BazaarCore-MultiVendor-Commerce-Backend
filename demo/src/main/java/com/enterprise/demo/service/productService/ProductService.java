package com.enterprise.demo.service.productService;

import com.enterprise.demo.dto.product.ProductRequest;
import com.enterprise.demo.dto.product.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductResponse addProduct(ProductRequest request);
    List<ProductResponse> getAllProduct();
    ProductResponse getProductById(Long id);
    ProductResponse updateProduct(Long id,ProductRequest request);
    void deleteProductById(Long id);
    void deleteAllProducts();

    void uploadProductImage(Long id, MultipartFile file);
}
