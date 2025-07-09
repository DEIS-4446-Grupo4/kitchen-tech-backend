package com.kitchenapp.kitchentech.payment.model;

import com.kitchenapp.kitchentech.payment.Enums.DocumentType;
import com.kitchenapp.kitchentech.payment.Enums.PaymentType;
import com.kitchenapp.kitchentech.payment.Enums.SaleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "date", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name="correlative", nullable = false, length = 50)
    private String correlative;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "amount", nullable = false)
    private Float amount;

    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "sale_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

}
