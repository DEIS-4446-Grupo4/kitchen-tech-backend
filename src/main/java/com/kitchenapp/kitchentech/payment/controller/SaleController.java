package com.kitchenapp.kitchentech.payment.controller;

import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.model.Client;
import com.kitchenapp.kitchentech.business.repository.AccountRepository;
import com.kitchenapp.kitchentech.business.repository.ClientRepository;
import com.kitchenapp.kitchentech.exception.ResourceNotFoundException;
import com.kitchenapp.kitchentech.payment.Enums.DocumentType;
import com.kitchenapp.kitchentech.payment.Enums.SaleStatus;
import com.kitchenapp.kitchentech.payment.model.Sale;
import com.kitchenapp.kitchentech.payment.repository.SaleRepository;
import com.kitchenapp.kitchentech.payment.service.SaleService;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/kitchentech/v1/sale")
public class SaleController {
    private final SaleService saleService;
    private final SaleRepository saleRepository;
    private final AccountRepository accountRepository;
    private final RestaurantRepository restaurantRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public SaleController(
            SaleService saleService,
            RestaurantRepository restaurantRepository,
            AccountRepository accountRepository,
            SaleRepository saleRepository,
            ClientRepository clientRepository
    ) {
        this.saleService = saleService;
        this.restaurantRepository = restaurantRepository;
        this.accountRepository = accountRepository;
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Sale>> getAllSalesByRestaurantId(@PathVariable(name = "restaurantId") Long restaurantId) {
        List<Sale> sales = saleService.getAllSalesByRestaurantId(restaurantId);

        if (sales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sales);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody Sale saleDto){
        Long clientId = null;
        if (saleDto.getClientId() != null) {
            clientId = clientRepository.findById(saleDto.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client not found"))
                    .getId();
        }

        Restaurant restaurant = restaurantRepository.findById(saleDto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Sale sale = new Sale();
        sale.setRestaurantId(restaurant.getId());
        sale.setDateTime(LocalDateTime.now());
        sale.setDocumentType(saleDto.getDocumentType());
        sale.setClientId(clientId);
        sale.setAmount(saleDto.getAmount());
        sale.setPaymentType(saleDto.getPaymentType());
        sale.setSaleStatus(SaleStatus.COMPLETED);

        String correlative = generateCorrelative(saleDto.getDocumentType());
        sale.setCorrelative(correlative);

        Sale savedSale = saleRepository.save(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSale);

    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Sale> cancelSale(@PathVariable Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        sale.setSaleStatus(SaleStatus.CANCELLED);
        saleRepository.save(sale);

        return ResponseEntity.ok(sale);
    }

    private String generateCorrelative(DocumentType type) {
        String prefix = type == DocumentType.TICKET ? "BW001-" : "FW001-";
        String lastCorrelative = saleRepository.findLastCorrelativeByDocumentType(type);

        int nextNumber = 1;
        if (lastCorrelative != null && lastCorrelative.startsWith(prefix)) {
            String numericPart = lastCorrelative.substring(prefix.length());
            try {
                nextNumber = Integer.parseInt(numericPart) + 1;
            } catch (NumberFormatException ignored) {}
        }

        return prefix + String.format("%06d", nextNumber);
    }
}