package com.kitchenapp.kitchentech.user.repository;

import com.kitchenapp.kitchentech.user.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    List<Restaurant> findAll();
    Optional<Restaurant> findByUsername(String username);
}
