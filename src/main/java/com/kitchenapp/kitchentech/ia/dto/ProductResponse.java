package com.kitchenapp.kitchentech.ia.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private String productName;
    private Double productPrice;
    private String category;
}
