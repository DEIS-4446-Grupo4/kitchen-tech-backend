package com.kitchenapp.kitchentech.business.service.iml;

import com.kitchenapp.kitchentech.business.Enums.State;
import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.model.AccountProduct;
import com.kitchenapp.kitchentech.business.repository.AccountRepository;
import com.kitchenapp.kitchentech.business.service.iml.AccountServiceImpl;
import com.kitchenapp.kitchentech.iot.model.TableRestaurant;
import com.kitchenapp.kitchentech.business.model.Client;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // Para que haga rollback y no quede la DB sucia después del test
public class AccountServiceIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountServiceImpl accountService;

    @Test
    public void createAccount_shouldSaveAndCalculateTotal() {
        Client client = new Client();
        client.setId(1L);

        TableRestaurant table = new TableRestaurant();
        table.setId(1L);

        AccountProduct product1 = new AccountProduct();
        product1.setPrice(10.0);
        product1.setQuantity(2); // total 20

        AccountProduct product2 = new AccountProduct();
        product2.setPrice(5.0);
        product2.setQuantity(3); // total 15

        Account account = new Account();
        account.setAccountName("Cuenta Test");
        account.setClient(client);
        account.setTable(table);
        account.setRestaurantId(1L);
        account.setState(State.InProgress);
        account.setDateCreated(LocalDateTime.now());
        account.setDateLog(LocalDateTime.now());
        account.setProducts(List.of(product1, product2));

        Account savedAccount = accountService.createAccount(account);

        assertNotNull(savedAccount.getId());

        Account accountFromDb = accountRepository.findById(savedAccount.getId()).orElse(null);
        assertNotNull(accountFromDb);
        assertEquals("Cuenta Test", accountFromDb.getAccountName());
        assertEquals(35.0, accountFromDb.getTotalAccount(), 0.01);
    }
}
