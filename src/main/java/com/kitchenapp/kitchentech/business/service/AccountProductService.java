package com.kitchenapp.kitchentech.business.service;

import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.model.AccountProduct;

public interface AccountProductService {
    AccountProduct addOrUpdateAccountProduct(Account account, AccountProduct accountProduct);
    AccountProduct getAccountProductByAccountAndProductId(Long accountId, Long productId);
    AccountProduct updateAccountProduct(Long accountId, Long productId, AccountProduct updatedAccountProduct);
    void deleteAccountProduct(Long id);
}
