package com.sourcecode.productservice.service;

import com.sourcecode.productservice.dto.ProductRequest;
import com.sourcecode.productservice.dto.ProductResponse;
import com.sourcecode.productservice.model.Product;
import com.sourcecode.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j // to add some logs after the product is saved
// in the service class we always create methods similar to that of the controller
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
//        first we have to map the productRequest with the Product model
//        by so doing we'll create an object instance of type product
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product); // we save the product object in the db
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepository.findAll(); // we first read all data in the db and put it in a variable of type List
        // as we did for createProduct we map the Product class with the ProductResponse class

        return products.stream().map(this::mapToProductResponse).toList(); // taking product as the argument and returning it as a list
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
