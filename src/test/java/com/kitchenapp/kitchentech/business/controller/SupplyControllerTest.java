package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.Enums.MetricUnit;
import com.kitchenapp.kitchentech.business.Enums.SupplyState;
import com.kitchenapp.kitchentech.business.model.Supply;
import com.kitchenapp.kitchentech.business.repository.SupplyRepository;
import com.kitchenapp.kitchentech.business.service.SupplyService;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SupplyControllerTest {

    @Test
    void getAllSupplies() {
    }

    @Test
    void getSupplyById() {
    }

    @Test
    void createSupply() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        Supply supply = new Supply();
        supply.setId(1L);
        supply.setSupplyName("Cebolla");
        supply.setCostPerUnit(0.37);
        supply.setCurrentlyOnStock(100D);
        supply.setSupplyCategory("Verdura");
        supply.setStateOfSupply(SupplyState.OnStock);
        supply.setMetricUnit(MetricUnit.u);
        supply.setRestaurantId(restaurant.getId());

        SupplyService supplyServiceMock = mock(SupplyService.class);
        when(supplyServiceMock.createSupply(any(Supply.class))).thenReturn(supply);
        SupplyController supplyController = new SupplyController(supplyServiceMock);

        // Act
        ResponseEntity<Supply> response = supplyController.createSupply(supply);

        //Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Cebolla", response.getBody().getSupplyName());
        assertEquals(0.37, response.getBody().getCostPerUnit(), 0);
        assertEquals(100D, response.getBody().getCurrentlyOnStock(), 0);
        assertEquals("Verdura", response.getBody().getSupplyCategory());
        assertEquals(SupplyState.OnStock, response.getBody().getStateOfSupply());
        assertEquals(MetricUnit.u, response.getBody().getMetricUnit());
        assertEquals(1L, response.getBody().getRestaurantId());
    }

    @Test
    void updateSupply() {
    }

    @Test
    void deleteSupply() {
    }
}