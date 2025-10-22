package com.example.orderservice.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "order-ıtems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String productId;
    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name ="order_id")
    private OrderModel order;
}
