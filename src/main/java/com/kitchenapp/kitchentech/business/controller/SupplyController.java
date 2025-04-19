package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.model.Supply;
import com.kitchenapp.kitchentech.business.service.SupplyService;
import com.kitchenapp.kitchentech.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/kitchentech/v1/supply")
public class SupplyController {
    private final SupplyService supplyService;
    @Autowired
    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    // URL: http://localhost:8080/api/kitchentech/v1/supply/restaurant/{restaurantId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Supply>> getAllSupplies(@PathVariable(name = "restaurantId") Long restaurantId) {
        if (supplyService.getAllSupplies(restaurantId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(supplyService.getAllSupplies(restaurantId), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/supply/{supplyId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{supplyId}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable(name = "supplyId") Long supplyId) {
        if(supplyService.getSupplyById(supplyId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(supplyService.getSupplyById(supplyId), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/supply
    // Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<Supply> createSupply(@RequestBody Supply supply) {
        try {
            supplyService.existsSupplyByName(supply);
            Supply createdSupply = supplyService.createSupply(supply);
            return new ResponseEntity<>(createdSupply, HttpStatus.CREATED);
        } catch (ValidationException e) {
            supplyService.existsSupplyByName(supply);
            return new ResponseEntity<>(supplyService.createSupply(supply), HttpStatus.BAD_REQUEST);
        }
    }

    // URL: http://localhost:8080/api/kitchentech/v1/supply/{supplyId}
    // Method: PUT
    @PutMapping("/{supplyId}")
    public ResponseEntity<Supply> updateSupply(@PathVariable(name = "supplyId") Long supplyId, @RequestBody Supply supply) {
        if(supplyService.getSupplyById(supplyId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(supplyService.updateSupply(supply), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/supply/{supplyId}
    // Method: DELETE
    @DeleteMapping("/{supplyId}")
    public ResponseEntity<String> deleteSupply(@PathVariable(name = "supplyId") Long supplyId) {
        if(supplyService.getSupplyById(supplyId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        supplyService.deleteSupply(supplyId);
        return new ResponseEntity<>("Supply deleted successfully", HttpStatus.OK);
    }
}
