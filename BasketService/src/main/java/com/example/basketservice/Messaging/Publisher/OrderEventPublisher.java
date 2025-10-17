package com.example.basketservice.Messaging.Publisher;

import com.example.basketservice.Config.RabbitMQConfig;
import com.example.basketservice.Model.OrderCompletedEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
@RequiredArgsConstructor
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publishOrderCompletedEvent(OrderCompletedEvent event){
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_ROUTING_KEY,
                event
        );
        log.info("Publishing order completed event: productId{},userId{},quantity{}"
                ,event.getProductId(),event.getUserId(),event.getQuantity());
    }

}
