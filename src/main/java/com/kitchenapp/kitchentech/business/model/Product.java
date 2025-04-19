package com.kitchenapp.kitchentech.business.model;

import com.kitchenapp.kitchentech.business.Dto.SupplyDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    @Column(name = "product_price", nullable = false, length = 10)
    private Double productPrice;

    @Column(name = "product_image_url", length = 250)
    private String productImageUrl;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @JoinColumn(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_supplies", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "supplies")
    private List<Supply> supplies = new ArrayList<>();
}
