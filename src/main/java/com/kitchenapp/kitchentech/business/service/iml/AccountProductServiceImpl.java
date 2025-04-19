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

    @Transactional
    public AccountProduct addOrUpdateAccountProduct(Account account, AccountProduct accountProduct) {
        Account accountS = accountService.getAccountById(account.getId());

        // Verificar si el producto ya existe en la cuenta
        AccountProduct existingProduct = accountProductRepository.findByAccountIdAndProductId(
                account.getId(), accountProduct.getProductId()).orElse(null);

        // Obtén los detalles del producto
        Product product = productService.getProductByRestaurantProductId(accountProduct.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("El producto con ID " + accountProduct.getProductId() + " no existe.");
        }

        if (existingProduct != null) {
            // Si existe, incrementamos la cantidad
            existingProduct.setQuantity(existingProduct.getQuantity() + accountProduct.getQuantity());
            accountS.updateTotalAccount();
            accountService.updateAccount(accountS);
            return accountProductRepository.save(existingProduct);
        } else {
            // Si no existe, asigna los detalles necesarios y guarda
            accountProduct.setAccountId(account.getId());

            accountProduct.setPrice(product.getProductPrice());  // Asigna el precio
            accountProduct.setProductName(product.getProductName());  // Asigna el nombre
            AccountProduct newProduct = accountProductRepository.save(accountProduct);
            accountS.getProducts().add(newProduct);

            accountS.updateTotalAccount();
            accountService.updateAccount(accountS);
            return newProduct;
        }
    }


    @Override
    public AccountProduct getAccountProductByAccountAndProductId(Long accountId, Long productId) {
        return accountProductRepository.findByAccountIdAndProductId(accountId, productId).orElse(null);
    }

    @Transactional
    public AccountProduct updateAccountProduct(Long accountId, Long productId, AccountProduct updatedAccountProduct) {
        // Verificar que la cuenta existe
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return null; // O lanzar una excepción
        }

        // Buscar el producto en la cuenta
        AccountProduct existingProduct = accountProductRepository.findById(productId).orElse(null);
        if (existingProduct == null || !existingProduct.getAccountId().equals(accountId)) {
            return null; // O lanzar una excepción
        }

        // Actualizar los detalles del producto
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
}
