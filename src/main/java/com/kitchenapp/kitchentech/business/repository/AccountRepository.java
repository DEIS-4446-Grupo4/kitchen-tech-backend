package com.kitchenapp.kitchentech.business.repository;

import com.kitchenapp.kitchentech.business.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByRestaurantId(Long restaurantId);
}