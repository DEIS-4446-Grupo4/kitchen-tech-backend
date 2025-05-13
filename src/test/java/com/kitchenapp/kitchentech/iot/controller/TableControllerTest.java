package com.kitchenapp.kitchentech.iot.controller;

import com.kitchenapp.kitchentech.iot.model.TableRestaurant;
import com.kitchenapp.kitchentech.iot.service.TableService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableControllerTest {

    @Test
    void getAllTables() {
        TableService tableServiceMock = mock(TableService.class);
        TableController tableController = new TableController(tableServiceMock);

        // Caso 1: Lista vacía
        when(tableServiceMock.getAllTables()).thenReturn(List.of());
        ResponseEntity<List<TableRestaurant>> responseEmpty = tableController.getAllTables();
        assertEquals(HttpStatus.NO_CONTENT, responseEmpty.getStatusCode());
        assertNull(responseEmpty.getBody());

        // Caso 2: Lista con elementos
        TableRestaurant table = new TableRestaurant();
        when(tableServiceMock.getAllTables()).thenReturn(List.of(table));
        ResponseEntity<List<TableRestaurant>> responseNotEmpty = tableController.getAllTables();
        assertEquals(HttpStatus.OK, responseNotEmpty.getStatusCode());
        assertNotNull(responseNotEmpty.getBody());
        assertEquals(1, responseNotEmpty.getBody().size());
    }

    @Test
    void getTableById() {
        TableService tableServiceMock = mock(TableService.class);
        TableController tableController = new TableController(tableServiceMock);

        // Caso 1: Mesa no encontrada
        when(tableServiceMock.getTableById(1L)).thenReturn(null);
        ResponseEntity<TableRestaurant> responseNotFound = tableController.getTableById(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
        assertNull(responseNotFound.getBody());

        // Caso 2: Mesa encontrada
        TableRestaurant table = new TableRestaurant();
        when(tableServiceMock.getTableById(1L)).thenReturn(table);
        ResponseEntity<TableRestaurant> responseFound = tableController.getTableById(1L);
        assertEquals(HttpStatus.OK, responseFound.getStatusCode());
        assertNotNull(responseFound.getBody());
        assertEquals(table, responseFound.getBody());
    }

    @Test
    void getAllTablesByRestaurantId() {
        TableService tableServiceMock = mock(TableService.class);
        TableController tableController = new TableController(tableServiceMock);

        // Caso 1: Lista vacía
        when(tableServiceMock.getAllTablesByRestaurantId(1L)).thenReturn(List.of());
        ResponseEntity<List<TableRestaurant>> responseEmpty = tableController.getAllTablesByRestaurantId(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEmpty.getStatusCode());
        assertNull(responseEmpty.getBody());

        // Caso 2: Lista con elementos
        TableRestaurant table = new TableRestaurant();
        when(tableServiceMock.getAllTablesByRestaurantId(1L)).thenReturn(List.of(table));
        ResponseEntity<List<TableRestaurant>> responseNotEmpty = tableController.getAllTablesByRestaurantId(1L);
        assertEquals(HttpStatus.OK, responseNotEmpty.getStatusCode());
        assertNotNull(responseNotEmpty.getBody());
        assertEquals(1, responseNotEmpty.getBody().size());
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
        // Arrange
        TableRestaurant existingTable = new TableRestaurant();
        existingTable.setId(1L);
        existingTable.setTableNumber(1L);

        TableRestaurant updatedTable = new TableRestaurant();
        updatedTable.setId(1L);
        updatedTable.setTableNumber(2L);

        TableService tableServiceMock = mock(TableService.class);
        when(tableServiceMock.getTableById(1L)).thenReturn(existingTable);
        when(tableServiceMock.updateTable(any(TableRestaurant.class))).thenReturn(updatedTable);

        TableController tableController = new TableController(tableServiceMock);

        // Act
        ResponseEntity<TableRestaurant> response = tableController.updateTable(1L, updatedTable);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals(2L, response.getBody().getTableNumber());
    }

    @Test
    void deleteTable() {
        TableService tableServiceMock = mock(TableService.class);
        TableController tableController = new TableController(tableServiceMock);

        // Caso 1: Mesa no encontrada
        when(tableServiceMock.getTableById(1L)).thenReturn(null);
        ResponseEntity<String> responseNotFound = tableController.deleteTable(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
        assertNull(responseNotFound.getBody());

        // Caso 2: Mesa eliminada
        TableRestaurant table = new TableRestaurant();
        when(tableServiceMock.getTableById(1L)).thenReturn(table);
        ResponseEntity<String> responseDeleted = tableController.deleteTable(1L);
        assertEquals(HttpStatus.OK, responseDeleted.getStatusCode());
        assertEquals("Table deleted successfully", responseDeleted.getBody());
    }
}