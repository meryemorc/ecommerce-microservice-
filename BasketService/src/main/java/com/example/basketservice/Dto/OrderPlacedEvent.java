package com.example.basketservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent {
    private String orderId;
    private Long userId;
    private String username;
    private String userEmail;
    private LocalDateTime orderDate;
    private List<OrderItem> items;
    private double totalAmount;
}


