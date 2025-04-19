package com.kitchenapp.kitchentech.user.Dto;

import com.kitchenapp.kitchentech.user.model.StaffUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {

    private Long id;
    private String username;
    private String password;
    private String phone;
    private String name;
    private String email;
    private String image;
    private String city;
    private String district;
    private String role;
    private List<StaffUser> staffUsers;
}
