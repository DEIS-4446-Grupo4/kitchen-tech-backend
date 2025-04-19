package com.kitchenapp.kitchentech.authentication.service;

import com.kitchenapp.kitchentech.authentication.model.AuthResponse;
import com.kitchenapp.kitchentech.authentication.model.LoginRequest;
import com.kitchenapp.kitchentech.authentication.model.RegisterRestaurantRequest;
import com.kitchenapp.kitchentech.authentication.model.RegisterStaffUserRequest;

public interface AuthService {
    public abstract AuthResponse registerRestaurant(RegisterRestaurantRequest registerRestaurantRequest);
    public abstract AuthResponse registerStaffUser(RegisterStaffUserRequest registerStaffUserRequest);
    public abstract AuthResponse login(LoginRequest loginRequest);
    public void existsUserByUsername(RegisterRestaurantRequest registerRequest, RegisterStaffUserRequest registerStaffUserRequest);
}
