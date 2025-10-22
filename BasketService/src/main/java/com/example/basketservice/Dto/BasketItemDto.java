package com.example.basketservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemDto {
    private String productId;
    private String productName;
    private Integer quantity;
    private Double price;
}