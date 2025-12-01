package com.kitchenapp.kitchentech.ia.controller;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.ia.services.IAProductCreator;
import com.kitchenapp.kitchentech.ia.services.IAService;
import com.kitchenapp.kitchentech.ia.dto.ProductResponse;
import com.kitchenapp.kitchentech.user.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webhook/whatsapp")
public class WhatsAppWebHookController {

    @Autowired
    private IAService iaService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private IAProductCreator iaProductCreator;

    @PostMapping
    public ResponseEntity<ProductResponse> receiveMessage(@RequestParam  Map<String, String> body){
        String phone = body.get("From");
        String message = body.get("Body");

        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // Obtener datos de la IA
            ProductResponse productResponse = iaService.classifyProduct(message);

            // Obtener restauranteId asociado al número de teléfono
            Long restaurantId = restaurantService.getRestaurantByPhone(phone).getId();

            // Crear el producto en la base de datos
            Product created = iaProductCreator.createProductFromIA(productResponse, restaurantId);

            return ResponseEntity.ok(productResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
