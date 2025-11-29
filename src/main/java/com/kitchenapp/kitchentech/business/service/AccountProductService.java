package com.kitchenapp.kitchentech.business.service;

import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.model.AccountProduct;

import java.util.List;

public interface AccountProductService {
    AccountProduct addOrUpdateDirect(AccountProduct accountProduct);
    List<AccountProduct> getProductsByAccountId(Long accountId);
    AccountProduct addOrUpdateAccountProduct(Account account, AccountProduct accountProduct);
    AccountProduct getAccountProductByAccountAndProductId(Long accountId, Long productId);
    AccountProduct updateAccountProduct(Long accountId, Long productId, AccountProduct updatedAccountProduct);
    void deleteAccountProduct(Long id);
}
