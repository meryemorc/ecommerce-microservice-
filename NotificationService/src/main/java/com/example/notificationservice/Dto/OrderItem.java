package com.example.notificationservice.Dto; // Notification Service için (Basket Service için de kendi paket yolunu kullanın)

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
}