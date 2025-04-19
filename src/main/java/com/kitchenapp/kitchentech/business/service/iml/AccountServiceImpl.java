package com.kitchenapp.kitchentech.business.service.iml;

import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.repository.AccountRepository;
import com.kitchenapp.kitchentech.business.service.AccountService;
import com.kitchenapp.kitchentech.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts(Long restaurantId) {
        return accountRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account createAccount(Account account) {
        // Actualiza el total antes de guardar la cuenta por primera vez
        account.updateTotalAccount();
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        Account accountToUpdate = getAccountById(account.getId());
        if (accountToUpdate != null) {
            accountToUpdate.setAccountName(account.getAccountName());
            accountToUpdate.setClient(account.getClient());
            accountToUpdate.setRestaurantId(account.getRestaurantId());
            accountToUpdate.setTable(account.getTable());
            accountToUpdate.setState(account.getState());
            accountToUpdate.setDateCreated(account.getDateCreated());
            accountToUpdate.setDateLog(account.getDateLog());
            accountToUpdate.setProducts(account.getProducts());
            accountToUpdate.updateTotalAccount();

            return accountRepository.save(accountToUpdate);
        }
        return null;
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
