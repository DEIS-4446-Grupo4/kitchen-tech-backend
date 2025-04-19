package com.kitchenapp.kitchentech.business.service;

import com.kitchenapp.kitchentech.business.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts(Long restaurantId);
    Account getAccountById(Long id);
    Account createAccount(Account account);
    Account updateAccount(Account account);
    void deleteAccount(Long id);
}
