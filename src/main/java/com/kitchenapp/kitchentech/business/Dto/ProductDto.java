package com.kitchenapp.kitchentech.business.Dto;

import com.kitchenapp.kitchentech.business.model.Supply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String productName;
    private Double productPrice;
    private String productImageUrl;
    private String category;
    private Long restaurantId;
    private List<Supply> supplies;
}