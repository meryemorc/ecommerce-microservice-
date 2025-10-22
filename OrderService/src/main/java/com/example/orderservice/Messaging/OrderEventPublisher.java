package com.example.orderservice.Messaging;

import com.example.orderservice.Dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.order}")
    private String orderExchange;

    @Value("${rabbitmq.routing.key.notification}")
    private String notificationRoutingKey;

    public void publishOrderEvent(OrderEvent orderEvent) {
        rabbitTemplate.convertAndSend(orderExchange, notificationRoutingKey, orderEvent);
    }
}
