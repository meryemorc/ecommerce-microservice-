package com.example.basketservice.Dto;


import com.example.basketservice.Model.BasketItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponseDto {

    private Long userId;
    private String username;
    private List<BasketItem> items;
    private Double totalPrice;



}
