package com.example.orderservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private Long orderId;
    private Long userId;
    private String username;
    private List<OrderItemDto> items;
    private Double totalAmount;
    private Date orderDate;
}



