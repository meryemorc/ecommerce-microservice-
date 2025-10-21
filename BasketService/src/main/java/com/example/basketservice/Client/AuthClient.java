package com.example.basketservice.Client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthClient {

    private final RestTemplate restTemplate;
    private static final String AUTH_SERVICE_URL = "http://localhost:8080/auth/validate";

    public Map<String, Object> validateToken(String token) {
        try {
            // Request body oluştur
            Map<String, String> request = new HashMap<>();
            request.put("token", token);

            // UserService'e validate isteği gönder
            Map<String, Object> response = restTemplate.postForObject(
                    AUTH_SERVICE_URL,
                    request,
                    Map.class
            );

            return response;
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return null;
        }
    }

}
