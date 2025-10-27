package com.example.basketservice.Controller;

import com.example.basketservice.Dto.AddItemRequestDto;
import com.example.basketservice.Dto.BasketResponseDto;
import com.example.basketservice.Dto.RemoveItemRequestDto;
import com.example.basketservice.Model.BasketModel;
import com.example.basketservice.Service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/add-item")
    public ResponseEntity<?> addItem(@RequestBody AddItemRequestDto request,
                                     HttpServletRequest httpRequest) {

        // Filter'dan userId ve username al
        Long userId = (Long) httpRequest.getAttribute("userId");
        String username = (String) httpRequest.getAttribute("username");

        // Request'e set et
        request.setUserId(userId);
        request.setUsername(username);

        basketService.addItemToBasket(request);
        return ResponseEntity.ok(request);
    }

    // BasketController.java
    @PostMapping("/remove-item")
    public ResponseEntity<?> removeItem(@RequestBody RemoveItemRequestDto request,
                                        HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        BasketResponseDto basket = basketService.removeItemFromBasket(userId, request.getProductId());
        return ResponseEntity.ok(basket); // ← Basket objesini döndür
    }

    // Endpoint: /api/basket/get-basket
    @GetMapping("/get-basket")
    public ResponseEntity<BasketModel> getBasket(HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        BasketModel response = basketService.getBasket(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-basket/{userId}")
    public void deleteBasket(@PathVariable Long userId){
        basketService.clearBasket(userId);
    }

    // BasketController.java
    @PostMapping("/complete-order")  // ← {userId} kaldır
    public ResponseEntity<?> completeOrder(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");

        basketService.completeOrder(userId);
        return ResponseEntity.ok("Sipariş tamamlandı");
    }

}
