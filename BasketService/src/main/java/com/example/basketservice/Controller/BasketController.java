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

    @DeleteMapping("/remove-item")
    public ResponseEntity<BasketResponseDto> removeItem(@RequestBody RemoveItemRequestDto removeRequest){
        if (removeRequest.getProductId() == null) {
            return ResponseEntity.badRequest().build();
        }
        BasketResponseDto response = basketService.removeItemFromBasket(removeRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-basket/{userId}")
    public ResponseEntity<BasketModel> getBasket(@PathVariable Long userId){
        BasketModel response = basketService.getBasket(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-basket/{userId}")
    public void deleteBasket(@PathVariable Long userId){
        basketService.clearBasket(userId);
    }

    @PostMapping("/complete-order/{userId}")
    public ResponseEntity<String> completeOrder(@PathVariable Long userId) {

        String message = basketService.completeOrder(userId);

        return ResponseEntity.ok(message);
    }

}
