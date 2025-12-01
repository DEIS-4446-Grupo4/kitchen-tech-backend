package com.kitchenapp.kitchentech.ia.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitchenapp.kitchentech.ia.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IAService {

    @Value("${ia.api.key}")
    private String API_KEY;

    private String getApiUrl() {
        return "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;
    }

    public ProductResponse classifyProduct(String nombreInput) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        String prompt = String.format(
                "Actúa como un administrador de restaurante. Analiza el ítem: '%s'.\n" +
                        "Genera un JSON estricto con solo 3 campos:\n" +
                        "1. nombre: El nombre del plato con formato correcto (Mayúsculas iniciales).\n" +
                        "2. categoria: Clasifícalo ÚNICAMENTE en una de estas opciones: ['Almuerzo', 'Postre', 'Sandwich', 'Bebida', 'Entrada'].\n" +
                        "3. precio: Un precio estimado en Soles Peruanos (PEN) como número decimal.\n\n" +
                        "Responde SOLO el JSON sin markdown ni caracteres adicionales.",
                nombreInput
        );

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> parts = new HashMap<>();

        parts.put("text", prompt);
        content.put("parts", List.of(parts));
        requestBody.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(getApiUrl(), entity, String.class);

            // Log raw response para depuración
            System.out.println("Respuesta raw de Google: " + response.getBody());

            JsonNode root = objectMapper.readTree(response.getBody());
            String textResponse = root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();

            // Limpiar caracteres indeseados
            textResponse = textResponse.replace("`", "")
                    .replace("\n", "")
                    .replace("⁠  json", "")
                    .replace("  ⁠", "")
                    .trim();

            // Extraer solo el bloque JSON con regex
            Pattern pattern = Pattern.compile("\\{.*\\}", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(textResponse);
            if (matcher.find()) {
                textResponse = matcher.group();
            } else {
                throw new RuntimeException("No se encontró JSON válido en la respuesta de la IA");
            }

            return objectMapper.readValue(textResponse, ProductResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Error en Opal Service: " + e.getMessage());
        }
    }
}
