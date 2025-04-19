package com.kitchenapp.kitchentech.user.service;

import com.kitchenapp.kitchentech.user.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    public abstract Restaurant createRestaurant(Restaurant restaurant);
    public abstract Restaurant getRestaurantById(Long id);
    public abstract Restaurant updateRestaurant(Restaurant restaurant);
    public abstract void deleteRestaurant(Long id);
    public abstract List<Restaurant> getAllRestaurants();
    public void existsRestaurantById(Long Id);
}
