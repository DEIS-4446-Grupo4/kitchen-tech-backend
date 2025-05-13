package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Test
    void getAllProductsByRestaurantId() {
    }

    @Test
    void getProductByRestaurantProductId() {
    }

    @Test
    void createProduct() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setProductName("Lomo Saltado");

        ProductService productServiceMock = mock(ProductService.class);
        when(productServiceMock.createProduct(any(Product.class))).thenReturn(product);

        ProductController productController = new ProductController(productServiceMock);

        // Act
        ResponseEntity<Product> response = productController.createProduct(product);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Lomo Saltado", response.getBody().getProductName());
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}