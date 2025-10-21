package com.example.basketservice.Service;

import com.example.basketservice.Dto.AddItemRequestDto;
import com.example.basketservice.Dto.BasketResponseDto;
import com.example.basketservice.Dto.RemoveItemRequestDto;
import com.example.basketservice.Messaging.Publisher.OrderEventPublisher;
import com.example.basketservice.Model.BasketItem;
import com.example.basketservice.Model.BasketModel;
import com.example.basketservice.Model.OrderCompletedEvent;
import com.example.basketservice.Repository.BasketRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
@Slf4j
public class BasketService {

    private final BasketRedisRepository basketRedisRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final UserServiceClient userServiceClient;


    public BasketResponseDto addItemToBasket(AddItemRequestDto request) {

        BasketModel basket = getOrCreateBasketModel(request.getUserId());

        BasketItem existingItem = basket.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            Integer newQuantity = existingItem.getQuantity() + request.getQuantity();
            boolean hasStock = userServiceClient.checkStock(request.getProductId(), newQuantity);

            if (!hasStock) {
                throw new RuntimeException("Stok yetersiz");
            }
            existingItem.setQuantity(newQuantity);
        } else {
            boolean hasStock = userServiceClient.checkStock(request.getProductId(), request.getQuantity());
            if (!hasStock) {
                log.error("stok yetersiz productId: {}, quantity: {}",
                        request.getProductId(), request.getQuantity());
                throw new RuntimeException("Stok yetersiz");
            }
            BasketItem newItem = new BasketItem(
                    request.getProductId(),
                    request.getProductName(),
                    request.getQuantity(),
                    request.getPrice()
            );

            basket.addItem(newItem);
        }

        basket.calculateTotalPrice();
        basketRedisRepository.save(basket);
        return convertToResponse(basket);
    }

    private BasketModel getOrCreateBasketModel(Long userId){
        BasketModel basketResponse = basketRedisRepository.findByUserId(userId);
        if (basketResponse == null) {
            BasketModel newBasket = new BasketModel();
            newBasket.setUserId(userId);
           basketRedisRepository.save(newBasket);
           return newBasket;
        }else{
            return basketResponse;
        }
    }
    
    private BasketResponseDto convertToResponse(BasketModel basket) {
        BasketResponseDto response = new BasketResponseDto();
        response.setUserId(basket.getUserId());
        response.setUsername(basket.getUsername());
        response.setItems(basket.getItems());
        response.setTotalPrice(basket.getTotalPrice());
        return response;
    }
    public BasketResponseDto removeItemFromBasket(RemoveItemRequestDto request) {
        BasketModel basket = basketRedisRepository.findByUserId(request.getUserId());
        if (basket == null) {
            throw new RuntimeException("Sepet bulunamadı");
        }

        basket.removeItem(request.getProductId());
        basketRedisRepository.save(basket);
        return convertToResponse(basket);
    }
    public BasketModel getBasket(Long userId) {

        BasketModel basket = getOrCreateBasketModel(userId);
        String username = userServiceClient.getUsernameById(userId);
        basket.setUsername(username);
        basket.calculateTotalPrice();
        return basket;
    }
    public void clearBasket(Long userId) {
        basketRedisRepository.deleteByUserId(userId);
        log.info("sepeti temizlenen: {}", userId);
    }

    public String completeOrder(Long userId) {

        BasketModel basket = basketRedisRepository.findByUserId(userId);

        if (basket == null || basket.getItems() == null || basket.getItems().isEmpty()) {
            throw new RuntimeException("Sepet boş veya bulunamadı!");
        }

        for (BasketItem item : basket.getItems()) {
            OrderCompletedEvent event = new OrderCompletedEvent(
                    item.getProductId(),
                    item.getQuantity(),
                    userId
            );

            orderEventPublisher.publishOrderCompletedEvent(event);

            log.info("Event sent: productId={}, quantity={}",
                    item.getProductId(), item.getQuantity());
        }
        clearBasket(userId);
        return "Siparişiniz alındı";
    }

}
