package com.example.orderservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketCheckoutEvent {
        private Long userId;
        private String username;
        private List<BasketItemDto> items;

    }

