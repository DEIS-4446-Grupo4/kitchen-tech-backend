package com.kitchenapp.kitchentech.authentication.controller;

import com.kitchenapp.kitchentech.authentication.model.AuthResponse;
import com.kitchenapp.kitchentech.authentication.model.LoginRequest;
import com.kitchenapp.kitchentech.authentication.model.RegisterRestaurantRequest;
import com.kitchenapp.kitchentech.authentication.model.RegisterStaffUserRequest;
import com.kitchenapp.kitchentech.authentication.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/kitchentech/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // URL: http://localhost:8080/api/kitchentech/v1/auth/register-restaurant
    // Method: POST
    @Transactional
    @PostMapping("/register-restaurant")
    public ResponseEntity<AuthResponse> registerRestaurant(@RequestBody RegisterRestaurantRequest request) {
        // Verificar si ya existe el nombre de usuario
        authService.existsUserByUsername(request, null);

        // Registrar el restaurante
        AuthResponse registeredRestaurant = authService.registerRestaurant(request);
        return new ResponseEntity<>(registeredRestaurant, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/auth/register-staff
    // Method: POST
    @Transactional
    @PostMapping("/register-staff")
    public ResponseEntity<AuthResponse> registerStaffUser(@RequestBody RegisterStaffUserRequest request) {
        // Verificar si ya existe el nombre de usuario
        authService.existsUserByUsername(null, request);

        // Registrar al staff
        AuthResponse registeredStaff = authService.registerStaffUser(request);
        return new ResponseEntity<>(registeredStaff, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/auth/login
    // Method: POST
    @Transactional(readOnly = true)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        AuthResponse loggedUser = authService.login(request);
        return new ResponseEntity<>(loggedUser, HttpStatus.OK);
    }
}
