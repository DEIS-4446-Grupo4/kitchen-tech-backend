package com.kitchenapp.kitchentech.business.service;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.user.model.Restaurant;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProductsByRestaurantId(Long restaurantId);
    public Product getProductByRestaurantProductId(Long id);
    public Product createProduct(Product product);
    public void deleteProduct(Long id);
    public Product updateProduct(Product product);
    public List<Product> getProductByCategory(String category);
}
