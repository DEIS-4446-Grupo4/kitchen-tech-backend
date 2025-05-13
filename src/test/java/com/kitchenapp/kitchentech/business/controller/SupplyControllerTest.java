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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SupplyControllerTest {

    @Test
    void getAllSupplies() {
        SupplyService supplyServiceMock = mock(SupplyService.class);
        SupplyController supplyController = new SupplyController(supplyServiceMock);

        // Caso 1: Lista vacía
        when(supplyServiceMock.getAllSupplies(1L)).thenReturn(List.of());
        ResponseEntity<List<Supply>> responseEmpty = supplyController.getAllSupplies(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEmpty.getStatusCode());
        assertNull(responseEmpty.getBody());

        // Caso 2: Lista con elementos
        Supply supply = new Supply();
        when(supplyServiceMock.getAllSupplies(1L)).thenReturn(List.of(supply));
        ResponseEntity<List<Supply>> responseNotEmpty = supplyController.getAllSupplies(1L);
        assertEquals(HttpStatus.OK, responseNotEmpty.getStatusCode());
        assertNotNull(responseNotEmpty.getBody());
    }

    @Test
    void getSupplyById() {
        SupplyService supplyServiceMock = mock(SupplyService.class);
        SupplyController supplyController = new SupplyController(supplyServiceMock);

        // Caso 1: Lista vacía
        when(supplyServiceMock.getSupplyById(1L)).thenReturn(null);
        ResponseEntity<Supply> responseEmpty = supplyController.getSupplyById(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseEmpty.getStatusCode());
        assertNull(responseEmpty.getBody());

        // Caso 2: Lista con elementos
        Supply supply = new Supply();
        when(supplyServiceMock.getSupplyById(1L)).thenReturn(supply);
        ResponseEntity<Supply> responseNotEmpty = supplyController.getSupplyById(1L);
        assertEquals(HttpStatus.OK, responseNotEmpty.getStatusCode());
        assertNotNull(responseNotEmpty.getBody());
        assertEquals(supply, responseNotEmpty.getBody());
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
        Supply existingSupply = new Supply();
        existingSupply.setId(1L);
        existingSupply.setSupplyName("Cebolla");
        existingSupply.setCostPerUnit(0.37);

        Supply updatedSupply = new Supply();
        updatedSupply.setId(1L);
        updatedSupply.setSupplyName("Cebolla");
        updatedSupply.setCostPerUnit(0.43);

        SupplyService supplyServiceMock = mock(SupplyService.class);
        when(supplyServiceMock.getSupplyById(1L)).thenReturn(existingSupply);
        when(supplyServiceMock.updateSupply(any(Supply.class))).thenReturn(updatedSupply);

        SupplyController supplyController = new SupplyController(supplyServiceMock);

        ResponseEntity<Supply> response = supplyController.updateSupply(1L, updatedSupply);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Cebolla", response.getBody().getSupplyName());
        assertEquals(0.43, response.getBody().getCostPerUnit(), 0);
    }

    @Test
    void deleteSupply() {
        SupplyService supplyServiceMock = mock(SupplyService.class);
        SupplyController supplyController = new SupplyController(supplyServiceMock);

        // Caso 1: El suministro no existe
        when(supplyServiceMock.getSupplyById(1L)).thenReturn(null);
        ResponseEntity<String> responseNotFound = supplyController.deleteSupply(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
        assertNull(responseNotFound.getBody());

        // Caso 2: El suministro se elimina correctamente
        Supply supply = new Supply();
        when(supplyServiceMock.getSupplyById(1L)).thenReturn(supply);
        ResponseEntity<String> responseDeleted = supplyController.deleteSupply(1L);
        assertEquals(HttpStatus.OK, responseDeleted.getStatusCode());
        assertEquals("Supply deleted successfully", responseDeleted.getBody());
    }
}