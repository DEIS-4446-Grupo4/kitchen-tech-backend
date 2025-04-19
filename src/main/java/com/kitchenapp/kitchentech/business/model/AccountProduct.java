package com.kitchenapp.kitchentech.business.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "account_products")
public class AccountProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "products_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @JoinColumn(name = "account_id", nullable = false)
    private Long accountId;
}
