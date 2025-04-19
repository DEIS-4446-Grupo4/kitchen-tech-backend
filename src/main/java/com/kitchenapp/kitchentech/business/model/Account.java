package com.kitchenapp.kitchentech.business.model;

import com.kitchenapp.kitchentech.business.Enums.State;
import com.kitchenapp.kitchentech.iot.model.TableRestaurant;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//La cuenta tiene que contener, la orden, el cliente y la mesa
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id")
    private TableRestaurant table;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name="state",nullable = false)
    private State state;

    @Column(name = "total_account",nullable = false)
    private Float totalAccount;

    @Column(name = "date_created",nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_log",nullable = false)
    private LocalDateTime dateLog;

    @OneToMany(mappedBy = "accountId", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<AccountProduct> products = new ArrayList<>();

    public void updateTotalAccount() {
        this.totalAccount = (float) products.stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();
    }

}
