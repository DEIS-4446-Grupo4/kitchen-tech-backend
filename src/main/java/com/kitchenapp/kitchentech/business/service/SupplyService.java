package com.kitchenapp.kitchentech.business.service;

import com.kitchenapp.kitchentech.business.model.Supply;

import java.util.List;

public interface SupplyService {
    public List<Supply> getAllSupplies(Long restaurantId);
    public Supply getSupplyById(Long id);
    public Supply createSupply(Supply supply);
    public void deleteSupply(Long id);
    public Supply updateSupply(Supply supply);
    public void existsSupplyByName(Supply supply);
}
