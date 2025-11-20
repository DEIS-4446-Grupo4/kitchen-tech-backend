package com.kitchenapp.kitchentech.business.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private Long id;
    private String accountName;
    private Long clientId;
    private Long tableId;
    private Float totalAccount;
}
