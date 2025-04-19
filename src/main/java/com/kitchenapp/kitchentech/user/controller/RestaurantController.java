package com.kitchenapp.kitchentech.user.controller;

import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.Dto.RestaurantDto;
import com.kitchenapp.kitchentech.user.Enums.Role;
import com.kitchenapp.kitchentech.user.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kitchentech/v1/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    // URL: http://localhost:8080/api/kitchentech/v1/restaurant
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/restaurant
    // Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<Restaurant> registerRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant newRestaurant = restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<Restaurant>(newRestaurant, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/restaurant/{restaurantId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable(name="restaurantId") Long restaurantId){
        restaurantService.existsRestaurantById(restaurantId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        return new ResponseEntity<Restaurant>(restaurant,HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/restaurant/{restaurantId}
    // Method: PUT
    @Transactional
    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurantById(@PathVariable(name="restaurantId") Long restaurantId, @RequestBody RestaurantDto restaurantDTO) {
        restaurantService.existsRestaurantById(restaurantId);

        // Mapeo manual del DTO a la entidad
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setUsername(restaurantDTO.getUsername());
        restaurant.setPassword(restaurantDTO.getPassword());
        restaurant.setName(restaurantDTO.getName());
        restaurant.setPhone(restaurantDTO.getPhone());
        restaurant.setEmail(restaurantDTO.getEmail());
        restaurant.setImage(restaurantDTO.getImage());
        restaurant.setCity(restaurantDTO.getCity());
        restaurant.setDistrict(restaurantDTO.getDistrict());

        // Convertir el String a Role
        restaurant.setRole(Role.valueOf(restaurantDTO.getRole().toUpperCase()));

        Restaurant restaurantSaved = restaurantService.updateRestaurant(restaurant);
        return new ResponseEntity<>(restaurantSaved, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/restaurant/{restaurantId}
    // Method: DELETE
    @Transactional
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> deleteRestaurantById(@PathVariable(name="restaurantId") Long restaurantId) {
        restaurantService.existsRestaurantById(restaurantId);
        restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}