package com.example.notificationservice.Service;


import com.example.notificationservice.Dto.OrderPlacedEvent;
import com.example.notificationservice.Model.Notification;
import com.example.notificationservice.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class NotificationService {

        private final NotificationRepository notificationRepository;
        private final EmailService emailService;

        public void processOrderNotification(OrderPlacedEvent orderEvent) {

            String recipientEmail = orderEvent.getUserEmail();
            if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
                throw new RuntimeException("Alıcı e-posta adresi bulunamadı. Mesaj tekrar denenmeli.");
            }


            Notification notification = new Notification();
            notification.setUserId(orderEvent.getUserId());
            notification.setOrderId(orderEvent.getOrderId());
            notification.setRecipientEmail(recipientEmail);

            notification.setMessage("Order confirmation for order #" + orderEvent.getOrderId());
            notification.setSent(false);

            notification = notificationRepository.save(notification);

            // --- 3. E-posta Gönderimi ---
            boolean emailSent = emailService.sendOrderConfirmation(orderEvent, recipientEmail);

            if (emailSent) {
                notification.setSent(true);
                notification.setSentAt(LocalDateTime.now());
                log.info("E-posta başarıyla gönderildi: {}", recipientEmail);

            } else {
                notification.setErrorMessage("Email sending failed");
                log.warn("E-posta gönderme başarısız oldu: {}", recipientEmail);
            }

            notificationRepository.save(notification);
        }
    }
