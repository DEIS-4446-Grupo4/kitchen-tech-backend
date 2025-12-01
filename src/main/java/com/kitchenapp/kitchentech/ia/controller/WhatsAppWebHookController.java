package com.kitchenapp.kitchentech.ia.controller;

import com.kitchenapp.kitchentech.business.model.Product;
import com.kitchenapp.kitchentech.ia.services.IAProductCreator;
import com.kitchenapp.kitchentech.ia.services.IAService;
import com.kitchenapp.kitchentech.ia.dto.ProductResponse;
import com.kitchenapp.kitchentech.user.model.Restaurant;
import com.kitchenapp.kitchentech.user.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/kitchentech/v1/webhook/whatsapp")
public class WhatsAppWebHookController {

    @Autowired
    private IAService iaService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private IAProductCreator iaProductCreator;

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestParam  Map<String, String> body){

        // 1. Extraer número y mensaje
        String rawPhone = body.get("From"); // Ej: "whatsapp:+51912345678"
        String message = body.get("Body");

        if (message == null || message.isEmpty()) {
            String twiml = "<Response><Message>Mensaje vacío. Intenta nuevamente.</Message></Response>";
            return ResponseEntity.badRequest()
                    .header("Content-Type", "application/xml")
                    .body(twiml);
        }

        // 2. Normalizar número para buscar en BD
        String phone = rawPhone.replace("whatsapp:", "").trim();
        if (phone.startsWith("+51")) {
            phone = phone.substring(3); // Remueve el código de país
        }

        Restaurant restaurant = restaurantService.getRestaurantByPhone(phone);
        if (restaurant == null) {
            String twiml = "<Response><Message>No se encontró un restaurante asociado a este número.</Message></Response>";
            return ResponseEntity.status(404)
                    .header("Content-Type", "application/xml")
                    .body(twiml);
        }

        try {
            // 1. Analizar producto con IA
            ProductResponse productResponse = iaService.classifyProduct(message);

            // 2. Obtener restaurante por número de teléfono
            Long restaurantId = restaurantService.getRestaurantByPhone(phone).getId();

            // 3. Crear producto en BD
            Product created = iaProductCreator.createProductFromIA(productResponse, restaurantId);

            // 4. Respuesta al usuario
            String reply = "Producto creado exitosamente:\n"
                    + "🍽 *" + created.getProductName() + "*\n"
                    + "Categoría: " + created.getCategory() + "\n"
                    + "Precio: S/ " + created.getProductPrice();

            // Twilio exige XML
            String twiml = "<Response><Message>" + reply + "</Message></Response>";

            return ResponseEntity.ok()
                    .header("Content-Type", "application/xml")
                    .body(twiml);
        } catch (Exception e) {

            String twiml = "<Response><Message>Error: "
                    + e.getMessage()
                    + "</Message></Response>";

            return ResponseEntity.status(500)
                    .header("Content-Type", "application/xml")
                    .body(twiml);
        }
    }
}
