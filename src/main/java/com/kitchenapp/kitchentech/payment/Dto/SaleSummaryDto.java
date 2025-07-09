package com.kitchenapp.kitchentech.payment.Dto;

public class SaleSummaryDto {
    private Long totalSales;
    private Double totalAmount;

    public SaleSummaryDto(Long totalSales, Double totalAmount) {
        this.totalSales = totalSales;
        this.totalAmount = totalAmount;
    }

    public Long getTotalSales() {
        return totalSales;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}


