package com.example.orderservice.Messaging;

import com.example.orderservice.Dto.BasketCheckoutEvent;
import com.example.orderservice.Service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
@Slf4j
public class BasketListener {
    private final OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.queue.basket}")
    public void handleBasketCheckout(BasketCheckoutEvent event) {
        try {
            orderService.createOrder(event);
            log.info("✅ Order created successfully");
        } catch (Exception e) {
            log.error("❌ Error creating order", e);
        }
    }
    }


