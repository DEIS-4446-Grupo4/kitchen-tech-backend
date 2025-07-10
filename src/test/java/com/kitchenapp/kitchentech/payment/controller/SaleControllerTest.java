package com.kitchenapp.kitchentech.payment.controller;

import com.kitchenapp.kitchentech.business.model.Client;
import com.kitchenapp.kitchentech.business.repository.AccountRepository;
import com.kitchenapp.kitchentech.business.repository.ClientRepository;
import com.kitchenapp.kitchentech.exception.ResourceNotFoundException;
import com.kitchenapp.kitchentech.payment.Dto.SaleSummaryDto;
import com.kitchenapp.kitchentech.payment.Enums.DocumentType;
import com.kitchenapp.kitchentech.payment.Enums.PaymentType;
import com.kitchenapp.kitchentech.payment.Enums.SaleStatus;
import com.kitchenapp.kitchentech.payment.model.Sale;
import com.kitchenapp.kitchentech.payment.repository.SaleRepository;
import com.kitchenapp.kitchentech.payment.service.SaleService;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SaleControllerTest {

    @Mock
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private SaleController saleController;

    private Sale saleRequest;
    private Sale savedSale;
    private Restaurant restaurant;
    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        client = new Client();
        client.setId(2L);
        client.setFullName("Test Client");

        saleRequest = new Sale();
        saleRequest.setRestaurantId(1L);
        saleRequest.setClientId(2L);
        saleRequest.setDocumentType(DocumentType.TICKET);
        saleRequest.setAmount(150.0F);
        saleRequest.setPaymentType(PaymentType.CASH);

        savedSale = new Sale();
        savedSale.setId(1L);
        savedSale.setRestaurantId(1L);
        savedSale.setClientId(2L);
        savedSale.setDocumentType(DocumentType.TICKET);
        savedSale.setCorrelative("BW001-000001");
        savedSale.setAmount(150.0F);
        savedSale.setPaymentType(PaymentType.CASH);
        savedSale.setSaleStatus(SaleStatus.COMPLETED);
        savedSale.setDateTime(LocalDateTime.now());

        when(saleRepository.findLastCorrelativeByDocumentType(any(DocumentType.class))).thenReturn(null);
        when(saleRepository.save(any(Sale.class))).thenReturn(savedSale);
    }

    @Test
    void createSale_Success() {
        // Arrange
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        // Act
        ResponseEntity<Sale> response = saleController.createSale(saleRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals(SaleStatus.COMPLETED, response.getBody().getSaleStatus());
        assertEquals("BW001-000001", response.getBody().getCorrelative());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    void createSale_RestaurantNotFound() {
        // Arrange
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            saleController.createSale(saleRequest);
        });

        verify(saleRepository, never()).save(any(Sale.class));
    }

    @Test
    void createSale_ClientNotFound() {
        // Arrange
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            saleController.createSale(saleRequest);
        });

        verify(saleRepository, never()).save(any(Sale.class));
    }

    @Test
    void cancelSale_Success() {
        // Arrange
        Sale existingSale = new Sale();
        existingSale.setId(1L);
        existingSale.setSaleStatus(SaleStatus.COMPLETED);

        when(saleRepository.findById(1L)).thenReturn(Optional.of(existingSale));
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<Sale> response = saleController.cancelSale(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(SaleStatus.CANCELLED, response.getBody().getSaleStatus());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    void cancelSale_NotFound() {
        // Arrange
        when(saleRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            saleController.cancelSale(99L);
        });

        verify(saleRepository, never()).save(any(Sale.class));
    }
}