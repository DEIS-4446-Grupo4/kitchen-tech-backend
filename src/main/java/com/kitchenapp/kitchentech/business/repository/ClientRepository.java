package com.kitchenapp.kitchentech.business.repository;

import com.kitchenapp.kitchentech.business.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    boolean existsById(Long Id);
    List<Client> findByRestaurantId(Long restaurantId);
    boolean existsByDocumentAndRestaurantId(String document, Long restaurantId);

}
