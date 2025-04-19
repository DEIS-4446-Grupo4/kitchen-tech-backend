package com.kitchenapp.kitchentech.iot.service.impl;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.iot.model.TableRestaurant;
import com.kitchenapp.kitchentech.iot.repository.TableRespository;
import com.kitchenapp.kitchentech.iot.service.TableService;
import jakarta.persistence.Table;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    private final TableRespository tableRespository;
    public TableServiceImpl(TableRespository tableRespository) {
        this.tableRespository = tableRespository;
    }

    @Override
    public List<TableRestaurant> getAllTablesByRestaurantId(Long restaurantId){
        return tableRespository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<TableRestaurant> getAllTables() {
        return tableRespository.findAll();
    }

    @Override
    public TableRestaurant getTableById(Long id) {
        return tableRespository.findById(id).orElse(null);
    }

    @Override
    public TableRestaurant createTable(TableRestaurant tableRestaurant) {
        return tableRespository.save(tableRestaurant);
    }

    @Override
    public void deleteTable(Long id) {
        tableRespository.deleteById(id);
    }

    @Override
    public TableRestaurant updateTable(TableRestaurant tableRestaurant) {
        TableRestaurant tableRestaurantToUpdate = tableRespository.findById(tableRestaurant.getId()).orElse(null);
        if (tableRestaurantToUpdate != null){
            tableRestaurantToUpdate.setTableCapacity(tableRestaurant.getTableCapacity());
            tableRestaurantToUpdate.setTableNumber(tableRestaurant.getTableNumber());
            tableRestaurantToUpdate.setTableGuests(tableRestaurant.getTableGuests());
            tableRestaurantToUpdate.setTableStatus(tableRestaurant.getTableStatus());
            tableRestaurantToUpdate.setRestaurantId(tableRestaurant.getRestaurantId());
            return tableRespository.save(tableRestaurantToUpdate);
        }
        else {
            return null;
        }
    }
}
