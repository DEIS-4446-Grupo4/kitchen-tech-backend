package com.kitchenapp.kitchentech.business.service.iml;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.model.Supply;
import com.kitchenapp.kitchentech.business.repository.ProductRepository;
import com.kitchenapp.kitchentech.business.repository.SupplyRepository;
import com.kitchenapp.kitchentech.business.service.ProductService;
import com.kitchenapp.kitchentech.business.service.SupplyService;
import com.kitchenapp.kitchentech.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {
    private final SupplyRepository supplyRepository;

    public SupplyServiceImpl(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    @Override
    public List<Supply> getAllSupplies(Long restaurantId){
        return supplyRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Supply getSupplyById(Long id){
        return supplyRepository.findById(id).orElse(null);
    }

    @Override
    public Supply createSupply(Supply supply){

        return supplyRepository.save(supply);
    }
    @Override
    public void deleteSupply(Long id){
        supplyRepository.deleteById(id);
    }

    @Override
    public Supply updateSupply(Supply supply) {
        Supply supplyToUpdate = supplyRepository.findById(supply.getId()).orElse(null);
        if (supplyToUpdate != null){
            supplyToUpdate.setStateOfSupply(supply.getStateOfSupply());
            supplyToUpdate.setSupplyName(supply.getSupplyName());
            supplyToUpdate.setSupplyCategory(supply.getSupplyCategory());
            supplyToUpdate.setCostPerUnit(supply.getCostPerUnit());
            supplyToUpdate.setCurrentlyOnStock(supply.getCurrentlyOnStock());
            supplyToUpdate.setMetricUnit(supply.getMetricUnit());
            return supplyRepository.save(supplyToUpdate);
        }
        else {
            return null;
        }
    }

    @Override
    public void existsSupplyByName(Supply supply) {
        if(supplyRepository.existsBySupplyName(supply.getSupplyName())) {
            throw new ValidationException("Already exists a supply with the same name");
        }
    }
}
