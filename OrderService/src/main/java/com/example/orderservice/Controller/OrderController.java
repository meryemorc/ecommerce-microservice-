package com.example.orderservice.Controller;

import com.example.orderservice.Model.OrderModel;
import com.example.orderservice.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderModel>> getUserOrders(@PathVariable Long userId) {
        List<OrderModel> orders = orderRepository.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable Long orderId) {
        OrderModel order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return ResponseEntity.ok(order);
    }
}