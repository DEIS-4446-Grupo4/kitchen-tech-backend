package com.kitchenapp.kitchentech.business.repository;

import com.kitchenapp.kitchentech.business.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findByRestaurantId(Long restaurantId);
    Boolean existsBySupplyName(String supplyName);
}
