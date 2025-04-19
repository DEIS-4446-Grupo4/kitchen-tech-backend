package com.kitchenapp.kitchentech.business.repository;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByRestaurantId(Long restaurantId);
    List<Product> findByCategory(String Category);
}
