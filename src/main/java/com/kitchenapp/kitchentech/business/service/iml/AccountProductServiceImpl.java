package com.kitchenapp.kitchentech.business.service.iml;

import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.model.AccountProduct;
import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.repository.AccountProductRepository;
import com.kitchenapp.kitchentech.business.repository.AccountRepository;
import com.kitchenapp.kitchentech.business.service.AccountProductService;
import com.kitchenapp.kitchentech.business.service.AccountService;
import com.kitchenapp.kitchentech.business.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountProductServiceImpl implements AccountProductService {
    private final AccountProductRepository accountProductRepository;
    private final AccountRepository accountRepository;
    private final ProductService productService;
    private final AccountService accountService;

    public AccountProductServiceImpl(AccountRepository accountRepository, AccountProductRepository accountProductRepository, ProductServiceImpl productService, AccountService accountService) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.accountProductRepository = accountProductRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public AccountProduct addOrUpdateAccountProduct(Account account, AccountProduct accountProduct) {
        if (account == null || account.getId() == null) {
            throw new IllegalArgumentException("Account and its id are required.");
        }

        Account accountS = accountService.getAccountById(account.getId());
        if (accountS == null) throw new IllegalArgumentException("Account not found: " + account.getId());

        // Find existing by account id & productId
        AccountProduct existingProduct = accountProductRepository
                .findByAccountIdAndProductId(account.getId(), accountProduct.getProductId())
                .orElse(null);

        // Validate product exists in catalogue
        Product product = productService.getProductByRestaurantProductId(accountProduct.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("El producto con ID " + accountProduct.getProductId() + " no existe.");
        }

        if (existingProduct != null) {
            // Increment quantity
            Integer addQty = accountProduct.getQuantity() != null ? accountProduct.getQuantity() : 0;
            existingProduct.setQuantity(existingProduct.getQuantity() + addQty);
            existingProduct.setPrice(product.getProductPrice());
            existingProduct.setProductName(product.getProductName());

            // update total and persist
            accountS.updateTotalAccount();
            accountService.updateAccount(accountS);
            return accountProductRepository.save(existingProduct);
        } else {
            // Prepare new AccountProduct
            AccountProduct newProduct = new AccountProduct();
            newProduct.setProductId(accountProduct.getProductId());
            newProduct.setProductName(product.getProductName());
            newProduct.setPrice(product.getProductPrice());
            newProduct.setQuantity(accountProduct.getQuantity() != null ? accountProduct.getQuantity() : 1);
            newProduct.setAccount(accountS);

            AccountProduct saved = accountProductRepository.save(newProduct);

            accountS.getProducts().add(saved);
            accountS.updateTotalAccount();
            accountService.updateAccount(accountS);

            return saved;
        }
    }

    @Override
    public AccountProduct getAccountProductByAccountAndProductId(Long accountId, Long productId) {
        return accountProductRepository.findByAccountIdAndProductId(accountId, productId).orElse(null);
    }

    @Override
    @Transactional
    public AccountProduct updateAccountProduct(Long accountId, Long accountProductId, AccountProduct updatedAccountProduct) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return null;
        }

        AccountProduct existingProduct = accountProductRepository.findById(accountProductId).orElse(null);
        if (existingProduct == null || existingProduct.getAccount() == null || !existingProduct.getAccount().getId().equals(accountId)) {
            return null;
        }

        existingProduct.setProductId(updatedAccountProduct.getProductId());
        existingProduct.setProductName(updatedAccountProduct.getProductName());
        existingProduct.setPrice(updatedAccountProduct.getPrice());
        existingProduct.setQuantity(updatedAccountProduct.getQuantity());

        Account accountS = accountService.getAccountById(accountId);
        accountS.updateTotalAccount();
        accountService.updateAccount(accountS);

        return accountProductRepository.save(existingProduct);
    }

    @Transactional
    public void deleteAccountProduct(Long accountProductId) {
        accountProductRepository.deleteById(accountProductId);
    }

    @Override
    @Transactional
    public AccountProduct addOrUpdateDirect(AccountProduct accountProduct) {
        // If provided account is set, ensure relationship
        if (accountProduct.getAccount() != null && accountProduct.getAccount().getId() != null) {
            Account acc = accountService.getAccountById(accountProduct.getAccount().getId());
            accountProduct.setAccount(acc);
        }
        AccountProduct saved = accountProductRepository.save(accountProduct);
        if (saved.getAccount() != null) {
            Account acc = accountService.getAccountById(saved.getAccount().getId());
            acc.updateTotalAccount();
            accountService.updateAccount(acc);
        }
        return saved;
    }

    @Override
    public List<AccountProduct> getProductsByAccountId(Long accountId) {
        return accountProductRepository.findAll().stream()
                .filter(p -> p.getAccount() != null && p.getAccount().getId().equals(accountId))
                .toList();
    }
}
