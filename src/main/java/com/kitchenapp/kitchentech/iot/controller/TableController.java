package com.kitchenapp.kitchentech.iot.controller;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.iot.model.TableRestaurant;
import com.kitchenapp.kitchentech.iot.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kitchentech/v1/table")
public class TableController {
    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // URL: http://localhost:8080/api/kitchentech/v1/table
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<TableRestaurant>> getAllTables() {
        if (tableService.getAllTables().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tableService.getAllTables(), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/table/{tableId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{tableId}")
    public ResponseEntity<TableRestaurant> getTableById(@PathVariable(name = "tableId") Long tableId) {
        if(tableService.getTableById(tableId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tableService.getTableById(tableId), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<TableRestaurant>> getAllTablesByRestaurantId(@PathVariable(name = "restaurantId") Long restaurantId) {
        List<TableRestaurant> tables = tableService.getAllTablesByRestaurantId(restaurantId);

        if (tables.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tables, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/table
    // Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<TableRestaurant> createTable(@RequestBody TableRestaurant tableRestaurant) {
        return new ResponseEntity<>(tableService.createTable(tableRestaurant), HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/table/{tableId}
    // Method: PUT
    @PutMapping("/{tableId}")
    public ResponseEntity<TableRestaurant> updateTable(@PathVariable(name = "tableId") Long tableId, @RequestBody TableRestaurant tableRestaurant) {
        if(tableService.getTableById(tableId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tableService.updateTable(tableRestaurant), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/table/{tableId}
    // Method: DELETE
    @DeleteMapping("/{tableId}")
    public ResponseEntity<String> deleteTable(@PathVariable(name = "tableId") Long tableId) {
        if(tableService.getTableById(tableId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tableService.deleteTable(tableId);
        return new ResponseEntity<>("Table deleted successfully", HttpStatus.OK);
    }
}
