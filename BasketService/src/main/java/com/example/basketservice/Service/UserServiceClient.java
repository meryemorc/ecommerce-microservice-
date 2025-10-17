package com.example.basketservice.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceClient {
    private final RestTemplate restTemplate;
    private static final String USER_SERVICE_URL =  "http://localhost:8080/users/id";

    public String getUsernameById(Long userId){
        String url = USER_SERVICE_URL +"/" + userId;
        UserResponse response = restTemplate.getForObject(url,UserResponse.class);
        if (response != null && response.getUsername() != null) {
            return response.getUsername();
        }
        return null;
    }
    @Data
    public static class UserResponse{
        private Long userId;
        private String username;
        private String email;
    }

    public boolean checkStock(String productId, Integer quantity){
        try{
            String url = "http://localhost:8081/products/" + productId + "/check-stock?quantity=" + quantity;
            Boolean response=restTemplate.getForObject(url,Boolean.class);
            if (response != null && response) {
                log.info("Stock uygun productId: {}", productId);
                return true;
            }
            return false;
        }
        catch(Exception e){
            log.error("Stock kontrol edilemedi: {}", e.getMessage());
            return false;
        }
    }
}
