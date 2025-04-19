package com.kitchenapp.kitchentech.business.model;

import com.kitchenapp.kitchentech.business.Enums.MetricUnit;
import com.kitchenapp.kitchentech.business.Enums.SupplyState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supply")
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supply_name", nullable = false, length = 50)
    private String supplyName;

    @Column(name = "supply_category", nullable = false, length = 50)
    private String supplyCategory;

    @Column (name = "metric_unit", nullable = false)
    private MetricUnit metricUnit;

    @Column (name = "currently_on_stock", nullable = false)
    private Double currentlyOnStock;

    @Column (name = "cost_per_unit", nullable = false)
    private Double costPerUnit;

    @Column (name = "state_of_supply", nullable = false, length = 50)
    private SupplyState stateOfSupply;

    @Column(name = "restaurant_id", nullable = false)
    private long restaurantId;
}
