package com.kitchenapp.kitchentech.business.model;

import com.kitchenapp.kitchentech.business.Enums.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name",  nullable = false, length = 150)
    private String fullName;

    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;
}
