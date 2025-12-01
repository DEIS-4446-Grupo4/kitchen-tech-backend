package com.kitchenapp.kitchentech.ia.services;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.repository.ProductRepository;
import com.kitchenapp.kitchentech.ia.dto.ProductResponse;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IAProductCreator {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Product createProductFromIA(ProductResponse iaData, Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Product p = new Product();
        p.setProductName(iaData.getProductName());
        p.setCategory(iaData.getCategory());
        p.setProductPrice(iaData.getProductPrice());
        p.setRestaurantId(restaurant.getId());

        return productRepository.save(p);
    }
}
