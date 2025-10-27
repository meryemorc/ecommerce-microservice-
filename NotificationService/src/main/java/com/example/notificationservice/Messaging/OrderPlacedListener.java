package com.example.notificationservice.Messaging;

import com.example.notificationservice.Config.RabbitMQConfig;
import com.example.notificationservice.Dto.OrderPlacedEvent; // String orderId'li DTO
import com.example.notificationservice.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderPlacedListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.ORDER_NOTIFICATION_QUEUE)
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {

        log.info("Sipariş ID: {} | Kullanıcı ID: {}", event.getOrderId(), event.getUserId());

        try {
            notificationService.processOrderNotification(event);
            log.info("Bildirim (E-posta/DB) İşlemi Başarıyla Tamamlandı. Order ID: {}", event.getOrderId());

        } catch (Exception e) {
            throw new RuntimeException("Bildirim İşlemi Başarısız: " + e.getMessage());
        }


    }
}

