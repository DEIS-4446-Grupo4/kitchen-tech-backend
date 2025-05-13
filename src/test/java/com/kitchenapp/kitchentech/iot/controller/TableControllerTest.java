package com.kitchenapp.kitchentech.iot.controller;

import com.kitchenapp.kitchentech.iot.model.TableRestaurant;
import com.kitchenapp.kitchentech.iot.service.TableService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableControllerTest {

    @Test
    void getAllTables() {
    }

    @Test
    void getTableById() {
    }

    @Test
    void getAllTablesByRestaurantId() {
    }

    @Test
    void createTable() {
        // Arrange
        TableRestaurant tableRestaurant = new TableRestaurant();
        tableRestaurant.setId(1L);
        tableRestaurant.setTableNumber(1L);

        TableService tableServiceMock = mock(TableService.class);
        when(tableServiceMock.createTable(any(TableRestaurant.class))).thenReturn(tableRestaurant);

        TableController tableController = new TableController(tableServiceMock);

        // Act
        ResponseEntity<TableRestaurant> response = tableController.createTable(tableRestaurant);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals(1L, response.getBody().getTableNumber());
    }

    @Test
    void updateTable() {
    }

    @Test
    void deleteTable() {
    }
}