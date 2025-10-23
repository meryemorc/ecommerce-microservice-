package com.example.basketservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {
    private String productId;
    private String productName; // <-- Notification Service (EmailService) için gerekli
    private int quantity;
    private double price;       // <-- Tüm servislerdeki birim fiyatın adı bu olmalı
}

