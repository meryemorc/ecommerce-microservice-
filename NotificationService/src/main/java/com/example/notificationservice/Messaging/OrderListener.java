package com.example.notificationservice.Messaging;

import com.example.notificationservice.Dto.OrderEventDto;
import com.example.notificationservice.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void handleOrderEvent(OrderEventDto orderEvent) {

        try {
            notificationService.processOrderNotification(orderEvent);
            log.info("sipariş bildirimi başarılı");
        } catch (Exception e) {
            log.error("sipariş bilidirimi oluşturulurken hata oluştu", e);
        }
    }
}
