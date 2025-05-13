package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.Enums.DocumentType;
import com.kitchenapp.kitchentech.business.model.Client;
import com.kitchenapp.kitchentech.business.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientControllerTest {

    @Test
    void getAllClients() {
        ClientService clientService = mock(ClientService.class);
        ClientController clientController = new ClientController(clientService);

        // Caso 1: Lista vacía
        when(clientService.getAllClients(1L)).thenReturn(List.of());
        ResponseEntity<List<Client>> responseEmpty = clientController.getAllClients(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEmpty.getStatusCode());
        assertNull(responseEmpty.getBody());

        // Caso 2: Lista con elementos
        Client client = new Client();
        when(clientService.getAllClients(1L)).thenReturn(List.of(client));
        ResponseEntity<List<Client>> responseNotEmpty = clientController.getAllClients(1L);
        assertEquals(HttpStatus.OK, responseNotEmpty.getStatusCode());
        assertNotNull(responseNotEmpty.getBody());
        assertEquals(1, responseNotEmpty.getBody().size());
    }


    @Test
    void getClientById() {
        ClientService clientService = mock(ClientService.class);
        ClientController clientController = new ClientController(clientService);

        // Caso 1: Cliente no encontrado
        when(clientService.getClientById(1L)).thenReturn(null);
        ResponseEntity<Client> responseNotFound = clientController.getClientById(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
        assertNull(responseNotFound.getBody());

        // Caso 2: Cliente encontrado
        Client client = new Client();
        when(clientService.getClientById(1L)).thenReturn(client);
        ResponseEntity<Client> responseFound = clientController.getClientById(1L);
        assertEquals(HttpStatus.OK, responseFound.getStatusCode());
        assertNotNull(responseFound.getBody());
        assertEquals(client, responseFound.getBody());
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
        // Arrange
        Client existingClient = new Client();
        existingClient.setId(1L);
        existingClient.setFullName("Llama Fencing SAC");
        existingClient.setDocumentType(DocumentType.RUC);
        existingClient.setDocument("20614099326");
        existingClient.setRestaurantId(1L);

        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setFullName("Llama Fencing SAC");
        updatedClient.setDocumentType(DocumentType.RUC);
        updatedClient.setDocument("20522312640");
        updatedClient.setRestaurantId(1L);


        ClientService clientServiceMock = mock(ClientService.class);
        when(clientServiceMock.getClientById(1L)).thenReturn(existingClient);
        when(clientServiceMock.updateClient(any(Client.class))).thenReturn(updatedClient);

        ClientController clientController = new ClientController(clientServiceMock);

        // Act
        ResponseEntity<Client> response = clientController.updateClient(1L, updatedClient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Llama Fencing SAC", response.getBody().getFullName());
        assertEquals(DocumentType.RUC, response.getBody().getDocumentType());
        assertEquals("20522312640", response.getBody().getDocument());
        assertEquals(1L, response.getBody().getRestaurantId());
    }

    @Test
    void deleteClient() {
        ClientService clientService = mock(ClientService.class);
        ClientController clientController = new ClientController(clientService);

        // Caso 1: Cliente no encontrado
        when(clientService.getClientById(1L)).thenReturn(null);
        ResponseEntity<String> responseNotFound = clientController.deleteClient(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
        assertNull(responseNotFound.getBody());

        // Caso 2: Cliente eliminado
        Client client = new Client();
        when(clientService.getClientById(1L)).thenReturn(client);
        ResponseEntity<String> responseDeleted = clientController.deleteClient(1L);
        assertEquals(HttpStatus.OK, responseDeleted.getStatusCode());
        assertNotNull(responseDeleted.getBody());
        assertEquals("Client deleted successfully", responseDeleted.getBody());
    }
}