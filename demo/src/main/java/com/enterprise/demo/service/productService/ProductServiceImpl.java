package com.enterprise.demo.service.productService;

import com.enterprise.demo.dto.product.ProductRequest;
import com.enterprise.demo.dto.product.ProductResponse;
import com.enterprise.demo.entity.Category;
import com.enterprise.demo.entity.Customer;
import com.enterprise.demo.entity.Product;
import com.enterprise.demo.mapper.ProductMapper;
import com.enterprise.demo.repository.CategoryRepository;
import com.enterprise.demo.repository.ProductRepository;
import com.enterprise.demo.service.mediaService.MediaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final MediaService mediaService;
    @Override
    public ProductResponse addProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        Product product = ProductMapper.toEntity(request,category);
        Product saved = productRepository.save(product);
        return buildProductResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = products.stream().map((this::buildProductResponse)).toList();
        return productResponses;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product saved = productRepository.findById(id).
                orElseThrow(()->new RuntimeException("Product Not Found"));

        return buildProductResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existing = productRepository.findById(id).
                orElseThrow(()->new RuntimeException("Product Not Found By Given Id"));
        Category category = request.getCategoryId()!=null
                ?categoryRepository.findById(request.getCategoryId()).
                orElseThrow(()->new RuntimeException("Product Not Found By Given Id"))
                :null;
        if(request.getName()!=null){
            existing.setName(request.getName());
        }
        if(request.getDescription()!=null){
            existing.setDescription(request.getDescription());
        }

        if(request.getBrand()!=null){
            existing.setBrand(request.getBrand());
        }
        if(request.getCategoryId()!=null){
            existing.setCategory(category);
        }

        Product saved=productRepository.save(existing);

        return buildProductResponse(saved);
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        Product saved = productRepository.findById(id).
                orElseThrow(()->new RuntimeException("Product Not Found"));
        productRepository.delete(saved);

    }

    @Override
    public void deleteAllProducts() {
      productRepository.deleteAll();

    }

    @Override
    @Transactional
    public void uploadProductImage(Long id, MultipartFile file) {
        if(file==null || file.isEmpty()){
            throw new RuntimeException("File is empty");
        }
        Product product = productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product Not Found With This Id"));
        String key = mediaService.uploadProductImage(id,file);
        product.setImageKey(key);
    }

    private ProductResponse buildProductResponse(Product product){
        ProductResponse dto = ProductMapper.toDto(product);
        if(product.getImageKey()!=null){
            String url = mediaService.getPublicUrl(product.getImageKey());
            dto.setImageUrl(url);
        }
        return dto;
    }
}
