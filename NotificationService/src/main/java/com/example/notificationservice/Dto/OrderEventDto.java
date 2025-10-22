package com.example.notificationservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDto {
    private Long orderId;
    private Long userId;
    private String username;
    private List<OrderItemDto> items;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
}
