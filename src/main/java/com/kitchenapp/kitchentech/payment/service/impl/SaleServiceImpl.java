package com.kitchenapp.kitchentech.payment.service.impl;

import com.kitchenapp.kitchentech.payment.Enums.DocumentType;
import com.kitchenapp.kitchentech.payment.model.Sale;
import com.kitchenapp.kitchentech.payment.repository.SaleRepository;
import com.kitchenapp.kitchentech.payment.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Sale getSaleByRestaurantSaleId(Long id){
        return saleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Sale> getAllSalesByRestaurantId(Long restaurantId) {
        return saleRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

}
