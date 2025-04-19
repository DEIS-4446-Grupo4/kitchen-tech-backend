package com.kitchenapp.kitchentech.iot.service;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.iot.model.TableRestaurant;

import java.util.List;

public interface TableService {
    public List<TableRestaurant> getAllTablesByRestaurantId(Long restaurantId);
    public List<TableRestaurant> getAllTables();
    public TableRestaurant getTableById(Long id);
    public TableRestaurant createTable(TableRestaurant tableRestaurant);
    public void deleteTable(Long id);
    public TableRestaurant updateTable(TableRestaurant tableRestaurant);
}
