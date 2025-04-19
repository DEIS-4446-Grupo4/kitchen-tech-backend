package com.kitchenapp.kitchentech.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private long id;
    private String token;
    private Long restaurantId;
    private String role;
}
