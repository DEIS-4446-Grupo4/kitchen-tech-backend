package com.kitchenapp.kitchentech.ia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webhook/whatsapp")
public class WhatsAppWebHookController {

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestParam  Map<String, String> body){
        String from = body.get("From");
        String message = body.get("Body");

        System.out.println("From: " + from + ": " + message);

        //Aqui insertare la logica del servicio de IA

        return ResponseEntity.ok("Message received");
    }
}
