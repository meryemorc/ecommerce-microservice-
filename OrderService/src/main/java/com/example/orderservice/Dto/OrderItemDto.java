package com.example.orderservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDto {
    private String productId;
    private String productName;
    private Integer quantity;
    private Double price;
}
