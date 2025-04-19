package com.kitchenapp.kitchentech.iot.repository;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.iot.model.TableRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRespository extends JpaRepository<TableRestaurant, Long> {
    List<TableRestaurant> findByRestaurantId(Long restaurantId);
}
