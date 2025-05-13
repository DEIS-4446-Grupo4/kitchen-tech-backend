package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.repository.ProductRepository;
import com.kitchenapp.kitchentech.business.service.ProductService;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

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
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setProductName("Lomo Saltado");
        product.setProductPrice(27.0);
        product.setProductImageUrl("https://www.google.com");
        product.setCategory("Almuerzo");
        product.setRestaurantId(1L);

        RestaurantRepository restaurantRepositoryMock = mock(RestaurantRepository.class);
        when(restaurantRepositoryMock.findById(1L)).thenReturn(Optional.of(restaurant));

        ProductService productServiceMock = mock(ProductService.class);
        ProductRepository productRepositoryMock = mock(ProductRepository.class);
        when(productRepositoryMock.save(any(Product.class))).thenReturn(product);

        ProductController productController = new ProductController(productServiceMock, restaurantRepositoryMock, productRepositoryMock);

        // Act
        ResponseEntity<Product> response = productController.createProduct(product);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Lomo Saltado", response.getBody().getProductName());
        assertEquals(27.0, response.getBody().getProductPrice(), 0);
        assertEquals("https://www.google.com", response.getBody().getProductImageUrl());
        assertEquals("Almuerzo", response.getBody().getCategory());
        assertEquals(1L, response.getBody().getRestaurantId());
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}