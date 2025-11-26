package com.kitchenapp.kitchentech.ia.dto;

import lombok.Data;

@Data
public class DishRequest {
    private String name;
    private String description;
    private Double price;
    private String category;
}
