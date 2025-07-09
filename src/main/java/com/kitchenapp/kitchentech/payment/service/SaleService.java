package com.kitchenapp.kitchentech.payment.service;

import com.kitchenapp.kitchentech.payment.model.Sale;

import java.util.List;

public interface SaleService {
    public Sale createSale(Sale sale);
    public Sale getSaleByRestaurantSaleId(Long id);
    List<Sale> getAllSalesByRestaurantId(Long restaurantId);
    void deleteSale(Long id);
}
