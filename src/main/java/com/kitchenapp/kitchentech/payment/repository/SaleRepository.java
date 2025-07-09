package com.kitchenapp.kitchentech.payment.repository;

import com.kitchenapp.kitchentech.payment.Enums.DocumentType;
import com.kitchenapp.kitchentech.payment.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByRestaurantId(Long restaurantId);

    // SaleRepository.java
    @Query("SELECT s.correlative FROM Sale s WHERE s.documentType = :type ORDER BY s.id DESC LIMIT 1")
    String findLastCorrelativeByDocumentType(@Param("type") DocumentType type);

}
