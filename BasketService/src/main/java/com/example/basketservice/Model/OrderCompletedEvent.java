package com.example.basketservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCompletedEvent implements Serializable {
    private String productId;
    private Integer quantity;
    private Long userId;
}
