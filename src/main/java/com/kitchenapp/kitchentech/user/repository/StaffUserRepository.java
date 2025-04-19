package com.kitchenapp.kitchentech.user.repository;

import com.kitchenapp.kitchentech.user.model.StaffUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffUserRepository extends JpaRepository<StaffUser, Long> {
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    List<StaffUser> findAll();
    Optional<StaffUser> findByUsername(String username);
}
