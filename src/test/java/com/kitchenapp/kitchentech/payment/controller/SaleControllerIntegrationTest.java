package com.kitchenapp.kitchentech.payment.controller;

import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitchenapp.kitchentech.business.model.Client;
import com.kitchenapp.kitchentech.business.repository.ClientRepository;
import com.kitchenapp.kitchentech.payment.Enums.DocumentType;
import com.kitchenapp.kitchentech.payment.Enums.PaymentType;
import com.kitchenapp.kitchentech.payment.Enums.SaleStatus;
import com.kitchenapp.kitchentech.payment.model.Sale;
import com.kitchenapp.kitchentech.payment.repository.SaleRepository;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class SaleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SaleRepository saleRepository;

    private Restaurant testRestaurant;
    private Client testClient;
    private Sale saleRequest;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        // Crear y guardar restaurante de prueba
        testRestaurant = new Restaurant();
        testRestaurant.setName("Restaurante de Prueba");
        testRestaurant.setUsername("Restaurante de Prueba");
        testRestaurant.setPassword("password");
        testRestaurant.setCity("Dirección de Prueba 123");
        testRestaurant.setEmail("test@restaurante.com"); // Añadir email
        testRestaurant = restaurantRepository.save(testRestaurant);

        // Crear y guardar cliente de prueba
        testClient = new Client();
        testClient.setFullName("Cliente de Prueba");
        testClient.setDocumentType(com.kitchenapp.kitchentech.business.Enums.DocumentType.DNI);
        testClient.setDocument("12345678");
        testClient.setRestaurantId(testRestaurant.getId());
        testClient = clientRepository.save(testClient);

        // Preparar solicitud de venta
        saleRequest = new Sale();
        saleRequest.setRestaurantId(testRestaurant.getId());
        saleRequest.setClientId(testClient.getId());
        saleRequest.setDocumentType(DocumentType.TICKET);
        saleRequest.setAmount(150.0F);
        saleRequest.setPaymentType(PaymentType.CASH);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createAndCancelSale_Integration() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/api/kitchentech/v1/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.correlative").exists())
                .andExpect(jsonPath("$.saleStatus").value(SaleStatus.COMPLETED.toString()))
                .andReturn();

        Sale createdSale = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                Sale.class
        );
        Long saleId = createdSale.getId();

        Optional<Sale> savedSaleOpt = saleRepository.findById(saleId);
        assertTrue(savedSaleOpt.isPresent());
        Sale savedSale = savedSaleOpt.get();
        assertEquals(SaleStatus.COMPLETED, savedSale.getSaleStatus());
        assertEquals(testRestaurant.getId(), savedSale.getRestaurantId());
        assertEquals(testClient.getId(), savedSale.getClientId());
        assertEquals(DocumentType.TICKET, savedSale.getDocumentType());
        assertEquals(150.0F, savedSale.getAmount());

        mockMvc.perform(put("/api/kitchentech/v1/sale/{id}/cancel", saleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saleId))
                .andExpect(jsonPath("$.saleStatus").value(SaleStatus.CANCELLED.toString()));

        Optional<Sale> cancelledSaleOpt = saleRepository.findById(saleId);
        assertTrue(cancelledSaleOpt.isPresent());
        assertEquals(SaleStatus.CANCELLED, cancelledSaleOpt.get().getSaleStatus());
    }

    @Test
    void createSale_InvalidData_Integration() throws Exception {
        // Crear venta con datos inválidos (sin restaurante)
        Sale invalidSale = new Sale();
        invalidSale.setClientId(testClient.getId());
        invalidSale.setDocumentType(DocumentType.TICKET);
        invalidSale.setAmount(150.0F);
        invalidSale.setPaymentType(PaymentType.CASH);

        mockMvc.perform(post("/api/kitchentech/v1/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSale)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void cancelSale_NotFound_Integration() throws Exception {
        // Intentar cancelar una venta que no existe
        Long nonExistentSaleId = 999999L;
        mockMvc.perform(put("/api/kitchentech/v1/sale/{id}/cancel", nonExistentSaleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
