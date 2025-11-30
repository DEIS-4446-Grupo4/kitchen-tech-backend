package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.Dto.AccountDTO;
import com.kitchenapp.kitchentech.business.Dto.AccountProductDto;
import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.model.AccountProduct;
import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.service.AccountProductService;
import com.kitchenapp.kitchentech.business.service.AccountService;
import com.kitchenapp.kitchentech.business.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kitchentech/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountProductService accountProductService;
    private final ProductService productService;

    public AccountController(AccountService accountService, AccountProductService accountProductService, ProductService productService) {
        this.accountService = accountService;
        this.accountProductService = accountProductService;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(@PathVariable(name="restaurantId")Long restaurantId){
        List<AccountDTO> accounts = accountService.getAllAccounts(restaurantId);
        if(accounts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<AccountDTO> dtos = accounts.stream()
                .map(a -> new AccountDTO(
                        a.getId(),
                        a.getAccountName(),
                        a.getTableId() != null ? a.getTableId() : null,
                        a.getTotalAccount()
                ))
                .toList();

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable(name = "accountId") Long accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        // Prepara productos (si vienen) asegurando la relación bidireccional
        if (account.getProducts() != null) {
            account.getProducts().forEach(ap -> ap.setAccount(account));
        }

        account.updateTotalAccount();
        Account createdAccount = accountService.createAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable(name = "accountId") Long accountId, @RequestBody Account account) {
        if (accountService.getAccountById(accountId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Ensure relationship for products coming in request
        if (account.getProducts() != null) {
            account.getProducts().forEach(ap -> ap.setAccount(account));
        }

        account.updateTotalAccount();
        Account updatedAccount = accountService.updateAccount(account);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable(name = "accountId") Long accountId) {
        if (accountService.getAccountById(accountId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/{accountId}/products")
    public ResponseEntity<AccountProductDto> addProductToAccount(
            @PathVariable(name = "accountId") Long accountId,
            @RequestBody AccountProduct accountProduct) {

        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Validar producto del catálogo
        Product product = productService.getProductByRestaurantProductId(accountProduct.getProductId());
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AccountProduct updatedProduct = accountProductService.addOrUpdateAccountProduct(account, accountProduct);

        if (updatedProduct == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        AccountProductDto responseDto = new AccountProductDto();
        responseDto.setQuantity(updatedProduct.getQuantity());
        responseDto.setPrice(updatedProduct.getPrice());
        responseDto.setProductName(updatedProduct.getProductName());
        responseDto.setProductId(updatedProduct.getProductId());
        responseDto.setId(updatedProduct.getId());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{accountId}/products/{accountProductId}")
    public ResponseEntity<AccountProduct> updateProductInAccount(
            @PathVariable(name = "accountId") Long accountId,
            @PathVariable(name = "accountProductId") Long accountProductId,
            @RequestBody AccountProduct accountProduct) {

        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccountProduct updatedProduct = accountProductService.updateAccountProduct(accountId, accountProductId, accountProduct);
        if (updatedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}/products/{accountProductId}")
    public ResponseEntity<Void> deleteProductInAccount(@PathVariable(name = "accountId") Long accountId,
                                                       @PathVariable(name = "accountProductId") Long accountProductId) {
        AccountProduct accountProduct = accountProductService.getAccountProductByAccountAndProductId(accountId, accountProductId);
        // accountProductId here may be the accountProduct id, so fallback to findById
        if (accountProduct == null) {
            // try by id
            // (accountProductService.deleteAccountProduct will handle if not found indirectly)
            // Still, respond NOT_FOUND to be explicit
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accountProductService.deleteAccountProduct(accountProduct.getId());

        Account account = accountService.getAccountById(accountId);
        if (account != null) {
            account.updateTotalAccount();
            accountService.updateAccount(account);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
