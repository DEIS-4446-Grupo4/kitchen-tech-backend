package com.kitchenapp.kitchentech.authentication.model;


import com.kitchenapp.kitchentech.user.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRestaurantRequest {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String image;
    private String city;
    private String district;
    private Role role;
}
