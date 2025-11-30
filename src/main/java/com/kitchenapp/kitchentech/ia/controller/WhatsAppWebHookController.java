package com.kitchenapp.kitchentech.ia.controller;

import com.kitchenapp.kitchentech.ia.IAService;
import com.kitchenapp.kitchentech.ia.dto.ProductResponse;
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

    @PostMapping
    public ResponseEntity<ProductResponse> receiveMessage(@RequestParam  Map<String, String> body){
        String from = body.get("From");
        String message = body.get("Body");

        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ProductResponse productResponse = iaService.classifyProduct(message);
            return ResponseEntity.ok(productResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
