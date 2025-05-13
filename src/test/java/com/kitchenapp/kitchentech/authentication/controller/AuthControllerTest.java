package com.kitchenapp.kitchentech.authentication.controller;

import com.kitchenapp.kitchentech.authentication.model.AuthResponse;
import com.kitchenapp.kitchentech.authentication.model.LoginRequest;
import com.kitchenapp.kitchentech.authentication.model.RegisterRestaurantRequest;
import com.kitchenapp.kitchentech.authentication.model.RegisterStaffUserRequest;
import com.kitchenapp.kitchentech.authentication.service.AuthService;
import com.kitchenapp.kitchentech.user.Enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Test
    void registerRestaurant() {
        // Arrange
        RegisterRestaurantRequest registerRestaurantRequest = new RegisterRestaurantRequest();
        registerRestaurantRequest.setUsername("345RC");
        registerRestaurantRequest.setRole(Role.ADMIN);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(1L);
        authResponse.setToken("token123");
        authResponse.setRestaurantId(10L);
        authResponse.setRole(Role.ADMIN.name());

        AuthService authServiceMock = mock(AuthService.class);
        when(authServiceMock.registerRestaurant(registerRestaurantRequest)).thenReturn(authResponse);
        AuthController authController = new AuthController(authServiceMock);

        // Act
        ResponseEntity<AuthResponse> response = authController.registerRestaurant(registerRestaurantRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("token123", response.getBody().getToken());
        assertEquals(10L, response.getBody().getRestaurantId());
        assertEquals(Role.ADMIN.name(), response.getBody().getRole());
    }

    @Test
    void registerStaffUser() {
        // Arrange
        RegisterStaffUserRequest registerStaffUserRequest = new RegisterStaffUserRequest();
        registerStaffUserRequest.setUsername("345RC");
        registerStaffUserRequest.setRole(Role.STAFF);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(1L);
        authResponse.setToken("token123");
        authResponse.setRestaurantId(10L);
        authResponse.setRole(Role.STAFF.name());

        AuthService authServiceMock = mock(AuthService.class);
        when(authServiceMock.registerStaffUser(registerStaffUserRequest)).thenReturn(authResponse);
        AuthController authController = new AuthController(authServiceMock);

        // Act
        ResponseEntity<AuthResponse> response = authController.registerStaffUser(registerStaffUserRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("token123", response.getBody().getToken());
        assertEquals(10L, response.getBody().getRestaurantId());
        assertEquals(Role.STAFF.name(), response.getBody().getRole());
    }

    @Test
    void loginUser() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("345RC");
        loginRequest.setPassword("password123");

        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(1L);
        authResponse.setToken("token123");
        authResponse.setRestaurantId(10L);
        authResponse.setRole(Role.ADMIN.name());

        AuthService authServiceMock = mock(AuthService.class);
        when(authServiceMock.login(loginRequest)).thenReturn(authResponse);
        AuthController authController = new AuthController(authServiceMock);

        // Act
        ResponseEntity<AuthResponse> response = authController.loginUser(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("token123", response.getBody().getToken());
        assertEquals(10L, response.getBody().getRestaurantId());
        assertEquals(Role.ADMIN.name(), response.getBody().getRole());
    }
}