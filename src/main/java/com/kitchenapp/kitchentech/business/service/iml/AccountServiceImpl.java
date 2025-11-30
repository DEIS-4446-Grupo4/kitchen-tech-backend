package com.kitchenapp.kitchentech.business.service.iml;

import com.kitchenapp.kitchentech.business.Dto.AccountDTO;
import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.repository.AccountRepository;
import com.kitchenapp.kitchentech.business.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDTO> getAllAccounts(Long restaurantId) {

        List<Account> accounts = accountRepository.findByRestaurantId(restaurantId);

        return accounts.stream()
                .map(a -> AccountDTO.builder()
                        .id(a.getId())
                        .accountName(a.getAccountName())
                        .tableId(a.getTable() != null ? a.getTable().getId() : null)
                        .restaurantId(a.getRestaurantId() != null ? a.getRestaurantId() : null)
                        .totalAccount(a.getTotalAccount() != null ? a.getTotalAccount() : 0f)
                        .build()
                )
                .toList();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account createAccount(Account account) {
        // Asegurarse de que cada AccountProduct apunte a la cuenta para que Cascade funcione
        if (account.getProducts() != null) {
            account.getProducts().forEach(ap -> ap.setAccount(account));
        }
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

            // Reemplazar lista de productos: asignar relación bidireccional correctamente
            accountToUpdate.getProducts().clear();
            if (account.getProducts() != null) {
                account.getProducts().forEach(ap -> {
                    ap.setAccount(accountToUpdate);
                    accountToUpdate.getProducts().add(ap);
                });
            }

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
