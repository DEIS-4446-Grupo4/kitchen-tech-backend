package com.kitchenapp.kitchentech.ia;

import com.kitchenapp.kitchentech.business.model.Account;
import com.kitchenapp.kitchentech.business.model.AccountProduct;
import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.business.service.AccountProductService;
import com.kitchenapp.kitchentech.business.service.AccountService;
import com.kitchenapp.kitchentech.business.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IAService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountProductService accountProductService;

    public String processMessage(String from, String message) {
        // Ejemplo básico de interpretación sin modelo
        // "Agregar 2 lomos saltados a mesa 4"
        Pattern p = Pattern.compile("(?i)agregar (\\d+) (.+?) a mesa (\\d+)");
        Matcher m = p.matcher(message);

        if (m.find()) {
            int quantity = Integer.parseInt(m.group(1));
            String productName = m.group(2).trim();
            int tableId = Integer.parseInt(m.group(3));

            Product product = productService.findByName(productName);
            if (product == null) return "Producto no encontrado";

            Account account = accountService.getOrCreateAccountForTable(tableId);
            accountProductService.addOrUpdateAccountProduct(account, new AccountProduct(product.getId(), quantity));

            return "✅ Se agregaron " + quantity + " " + productName + " a la mesa " + tableId;
        }
        return "No entendí el mensaje, por favor usa el formato: 'Agregar <cantidad> <producto> a mesa <número>'";
    }
}
