package com.kitchenapp.kitchentech.payment.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDto {
    private Long restaurantId;
    private LocalDateTime dateTime;
    private String documentType;
    private String correlative;
    private Long clientId;
    private Float amount;
    private String paymentType;
    private String saleStatus;
}
