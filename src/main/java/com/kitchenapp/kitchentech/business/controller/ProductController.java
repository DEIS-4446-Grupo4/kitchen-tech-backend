package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.Dto.ProductDto;
import com.kitchenapp.kitchentech.business.repository.ProductRepository;
import com.kitchenapp.kitchentech.business.service.ProductService;
import com.kitchenapp.kitchentech.exception.ResourceNotFoundException;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kitchentech/v1/product")
public class ProductController {

    private final ProductService productService;
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService, RestaurantRepository restaurantRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.restaurantRepository = restaurantRepository;
        this.productRepository = productRepository;
    }

    // URL: http://localhost:8080/api/kitchentech/v1/product/restaurant/{restaurantId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Product>> getAllProductsByRestaurantId(@PathVariable(name = "restaurantId") Long restaurantId) {
        List<Product> products = productService.getAllProductsByRestaurantId(restaurantId);

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/product/{productId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductByRestaurantProductId(@PathVariable(name = "productId") Long productId) {
        if(productService.getProductByRestaurantProductId(productId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productService.getProductByRestaurantProductId(productId), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/product
    // Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDTO) {
        Restaurant restaurant = restaurantRepository.findById(productDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductImageUrl(productDTO.getProductImageUrl());
        product.setCategory(productDTO.getCategory());
        product.setRestaurantId(restaurant.getId());
        product.setSupplies(productDTO.getSupplies());

        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/product/{productId}
    // Method: PUT
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "productId") Long productId, @RequestBody Product product) {
        if(productService.getProductByRestaurantProductId(productId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/product/{productId}
    // Method: DELETE
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "productId") Long productId) {
        if(productService.getProductByRestaurantProductId(productId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

}
