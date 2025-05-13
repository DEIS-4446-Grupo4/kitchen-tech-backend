package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.Enums.DocumentType;
import com.kitchenapp.kitchentech.business.model.Client;
import com.kitchenapp.kitchentech.business.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientControllerTest {

    @Test
    void getAllClients() {
    }

    @Test
    void getClientById() {
    }

    @Test
    void createClient() {
        // Arrange
        Client client = new Client();
        client.setId(1L);
        client.setFullName("Llama Fencing SAC");
        client.setDocumentType(DocumentType.RUC);
        client.setDocument("20614099326");
        client.setRestaurantId(1L);

        ClientService clientServiceMock = mock(ClientService.class);
        when(clientServiceMock.createClient(client)).thenReturn(client);
        ClientController clientController = new ClientController(clientServiceMock);

        // Act
        ResponseEntity<Client> response = clientController.createClient(client);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Llama Fencing SAC", response.getBody().getFullName());
        assertEquals(DocumentType.RUC, response.getBody().getDocumentType());
        assertEquals("20614099326", response.getBody().getDocument());
        assertEquals(1L, response.getBody().getRestaurantId());
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClient() {
    }
}