package com.kitchenapp.kitchentech.user.service.impl;

import com.kitchenapp.kitchentech.exception.ValidationException;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import com.kitchenapp.kitchentech.user.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant){
        Restaurant existingRestaurant = getRestaurantById(restaurant.getId());
        if(existingRestaurant != null){
            Restaurant restaurantToUpdate = existingRestaurant;
            restaurantToUpdate.setUsername(restaurant.getUsername());
            restaurantToUpdate.setName(restaurant.getName());
            restaurantToUpdate.setPhone(restaurant.getPhone());
            restaurantToUpdate.setEmail(restaurant.getEmail());
            restaurantToUpdate.setImage(restaurant.getImage());
            restaurantToUpdate.setCity(restaurant.getCity());
            restaurantToUpdate.setDistrict(restaurant.getDistrict());
            return restaurantRepository.save(restaurantToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public void existsRestaurantById(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ValidationException("No existe ningun restaurante con ese id");
        }
    }
}
