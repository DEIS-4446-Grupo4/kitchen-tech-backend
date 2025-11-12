package com.kitchenapp.kitchentech.ia.controller;

import com.kitchenapp.kitchentech.business.Dto.AccountProductDto;
import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.model.AccountProduct;
import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.service.AccountProductService;
import com.kitchenapp.kitchentech.business.service.AccountService;
import com.kitchenapp.kitchentech.business.service.ProductService;
import com.kitchenapp.kitchentech.ia.dto.IAProductInput;
import com.kitchenapp.kitchentech.ia.dto.IARequest;
import com.kitchenapp.kitchentech.iot.service.TableService;
import com.kitchenapp.kitchentech.business.Enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ia/")
public class IAAccountController {
    @Autowired
    private TableService tableService; // servicio que valida mesas

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountProductService accountProductService;

    @Autowired
    private ProductService productService;

    @PostMapping("/account-actions")
    public ResponseEntity<?> handleIAAction(@RequestBody IARequest iaRequest) {

        // 1. Validar mesa
        var mesa = tableService.getTableById(iaRequest.getTableId());
        if (mesa == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La mesa " + iaRequest.getTableId() + " no existe.");
        }

        // 2. Buscar si ya existe una cuenta para esta mesa
        Account account = accountService.getAccountById(iaRequest.getTableId());

        if (account == null) {
            // 3. Crear cuenta vacía
            account = new Account();
            account.setAccountName("Mesa " + iaRequest.getTableId());
            account.setRestaurantId(iaRequest.getRestaurantId());
            // setState ahora recibe un enum State en lugar de int
            account.setState(State.values()[0]); // equivalente a estado 0
            // setTotalAccount recibe Float
            account.setTotalAccount(0f);
            account.setDateCreated(LocalDateTime.now());
            account.setDateLog(LocalDateTime.now());
            account.setProducts(new ArrayList<>());

            account.updateTotalAccount();
            account = accountService.createAccount(account);
        }

        // 4. Agregar productos (si los hay)
        List<AccountProductDto> productsAdded = new ArrayList<>();

        if (iaRequest.getProducts() != null) {
            for (IAProductInput p : iaRequest.getProducts()) {

                // Validar producto
                Product product = productService.getProductByRestaurantProductId(p.getProductId());
                if (product == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Producto con ID " + p.getProductId() + " no existe.");
                }

                AccountProduct ap = new AccountProduct();
                ap.setProductId(p.getProductId());
                ap.setQuantity(p.getQuantity());
                ap.setPrice(product.getProductPrice());

                AccountProduct updated = accountProductService.addOrUpdateAccountProduct(account, ap);

                AccountProductDto dto = new AccountProductDto();
                dto.setQuantity(updated.getQuantity());
                dto.setPrice(updated.getPrice());
                productsAdded.add(dto);
            }
        }

        // 5. Respuesta para WhatsApp / OPAL
        Map<String, Object> response = new HashMap<>();
        response.put("mesaId", iaRequest.getTableId());
        response.put("productosAgregados", productsAdded);

        return ResponseEntity.ok(response);
    }
}