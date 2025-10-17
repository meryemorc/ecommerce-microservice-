package com.example.basketservice.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketModel {
    private Long userId;
    private String username;
    private List<BasketItem> items = new ArrayList<>();
    private Double totalPrice  =0.0;

    public void addItem(BasketItem item) {
        if (item == null || item.getProductId() == null) {
            log.error("Geçersiz item ekleme denemesi!");
            return;
        }
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }
    public void removeItem(Long productId) {
        if (productId == null) {
            log.warn("Silinmek istenen productId null!");
            return;
        }
        items.removeIf(item ->
                item != null &&
                        item.getProductId() != null &&
                        item.getProductId().equals(productId)
        );
    }
    public void calculateTotalPrice(){
        totalPrice=items.stream().mapToDouble(item ->item.getPrice() * item.getQuantity()).sum();
    }
}
