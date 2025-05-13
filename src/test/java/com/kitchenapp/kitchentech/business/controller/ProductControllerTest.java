package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.repository.ProductRepository;
import com.kitchenapp.kitchentech.business.service.ProductService;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Test
    void getAllProductsByRestaurantId() {
        ProductService productService = mock(ProductService.class);
        ProductRepository productRepositoryMock = mock(ProductRepository.class);
        RestaurantRepository restaurantRepositoryMock = mock(RestaurantRepository.class);
        ProductController productController = new ProductController(productService, restaurantRepositoryMock, productRepositoryMock);

        // Caso 1: Lista vacía
        when(productService.getAllProductsByRestaurantId(1L)).thenReturn(List.of());
        ResponseEntity<List<Product>> responseEmpty = productController.getAllProductsByRestaurantId(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEmpty.getStatusCode());
        assertNull(responseEmpty.getBody());

        // Caso 2: Lista con elementos
        Product product = new Product();
        when(productService.getAllProductsByRestaurantId(1L)).thenReturn(List.of(product));
        ResponseEntity<List<Product>> responseNotEmpty = productController.getAllProductsByRestaurantId(1L);
        assertEquals(HttpStatus.OK, responseNotEmpty.getStatusCode());
        assertNotNull(responseNotEmpty.getBody());
        assertEquals(1, responseNotEmpty.getBody().size());
    }

    @Test
    void getProductByRestaurantProductId() {
        ProductService productService = mock(ProductService.class);
        ProductRepository productRepositoryMock = mock(ProductRepository.class);
        RestaurantRepository restaurantRepositoryMock = mock(RestaurantRepository.class);
        ProductController productController = new ProductController(productService, restaurantRepositoryMock, productRepositoryMock);

        // Caso 1: Producto no encontrado
        when(productService.getProductByRestaurantProductId(1L)).thenReturn(null);
        ResponseEntity<Product> responseNotFound = productController.getProductByRestaurantProductId(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
        assertNull(responseNotFound.getBody());

        // Caso 2: Producto encontrado
        Product product = new Product();
        when(productService.getProductByRestaurantProductId(1L)).thenReturn(product);
        ResponseEntity<Product> responseFound = productController.getProductByRestaurantProductId(1L);
        assertEquals(HttpStatus.OK, responseFound.getStatusCode());
        assertNotNull(responseFound.getBody());
        assertEquals(product, responseFound.getBody());
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
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setProductName("Lomo Saltado");
        existingProduct.setProductPrice(27.0);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setProductName("Lomo Saltado");
        updatedProduct.setProductPrice(30.0);

        RestaurantRepository restaurantRepositoryMock = mock(RestaurantRepository.class);
        when(restaurantRepositoryMock.findById(1L)).thenReturn(Optional.of(new Restaurant()));

        ProductRepository productRepositoryMock = mock(ProductRepository.class);
        when(productRepositoryMock.findById(1L)).thenReturn(Optional.of(existingProduct));

        ProductService productServiceMock = mock(ProductService.class);
        when(productRepositoryMock.save(any(Product.class))).thenReturn(updatedProduct);
        when(productServiceMock.getProductByRestaurantProductId(1L)).thenReturn(existingProduct);
        when(productServiceMock.updateProduct(any(Product.class))).thenReturn(updatedProduct);

        ProductController productController = new ProductController(productServiceMock, restaurantRepositoryMock, productRepositoryMock);

        ResponseEntity<Product> response = productController.updateProduct(1L, updatedProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Lomo Saltado", response.getBody().getProductName());
        assertEquals(30.0, response.getBody().getProductPrice(), 0);

    }

    @Test
    void deleteProduct() {
        ProductService productServiceMock = mock(ProductService.class);
        ProductController productController = new ProductController(productServiceMock, null, null);

        // Caso 1: Producto no encontrado
        when(productServiceMock.getProductByRestaurantProductId(1L)).thenReturn(null);
        ResponseEntity<String> responseNotFound = productController.deleteProduct(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
        assertNull(responseNotFound.getBody());

        // Caso 2: Producto eliminado
        Product product = new Product();
        when(productServiceMock.getProductByRestaurantProductId(1L)).thenReturn(product);
        ResponseEntity<String> responseEntity = productController.deleteProduct(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Product deleted successfully", responseEntity.getBody());
    }
}