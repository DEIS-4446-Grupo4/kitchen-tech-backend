package com.kitchenapp.kitchentech.ia.dto;

import lombok.Data;

import java.util.List;

@Data
public class IARequest {
    private Long tableId;
    private Long restaurantId;
    private List<IAProductInput> products;
}