package com.example.notificationservice.Service;

import com.example.notificationservice.Dto.OrderEventDto;
import com.example.notificationservice.Model.Notification;
import com.example.notificationservice.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public void processOrderNotification(OrderEventDto orderEvent) {
        Notification notification = new Notification();
        notification.setUserId(orderEvent.getUserId());
        notification.setOrderId(orderEvent.getOrderId());
        notification.setRecipientEmail("temp@example.com");
        notification.setMessage("Order confirmation for order #" + orderEvent.getOrderId());
        notification.setSent(false);

        notification = notificationRepository.save(notification);

        boolean emailSent = emailService.sendOrderConfirmation(orderEvent, notification.getRecipientEmail());
        if (emailSent) {
            notification.setSent(true);
            notification.setSentAt(LocalDateTime.now());

        } else {
            notification.setErrorMessage("Email sending failed");

        }
        notificationRepository.save(notification);
    }
}
