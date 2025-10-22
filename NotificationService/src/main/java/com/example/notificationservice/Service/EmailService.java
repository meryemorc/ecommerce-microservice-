package com.example.notificationservice.Service;

import com.example.notificationservice.Dto.OrderEventDto;
import com.example.notificationservice.Dto.OrderItemDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public boolean sendOrderConfirmation(OrderEventDto orderEvent, String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");


            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Siparişiniz Alındı - #" + orderEvent.getOrderId());
            helper.setText(buildEmailContent(orderEvent), true);

            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
    private String buildEmailContent(OrderEventDto orderEvent) {
        StringBuilder html = new StringBuilder();

        html.append("<html><body style='font-family: Arial, sans-serif;'>");
        html.append("<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd;'>");
        html.append("<h2 style='color: #4CAF50;'> Siparişiniz Alındı!</h2>");
        html.append("<p>Merhaba <strong>").append(orderEvent.getUsername()).append("</strong>,</p>");
        html.append("<p>Sipariş numaranız: <strong>#").append(orderEvent.getOrderId()).append("</strong></p>");
        html.append("<h3>Sipariş Detayları:</h3>");
        html.append("<table style='width:100%; border-collapse: collapse;'>");
        html.append("<tr style='background-color: #f2f2f2;'>");
        html.append("<th style='padding: 10px; border: 1px solid #ddd;'>Ürün</th>");
        html.append("<th style='padding: 10px; border: 1px solid #ddd;'>Adet</th>");
        html.append("<th style='padding: 10px; border: 1px solid #ddd;'>Fiyat</th>");
        html.append("</tr>");

        for (OrderItemDto item : orderEvent.getItems()) {
            html.append("<tr>");
            html.append("<td style='padding: 10px; border: 1px solid #ddd;'>").append(item.getProductName()).append("</td>");
            html.append("<td style='padding: 10px; text-align: center; border: 1px solid #ddd;'>").append(item.getQuantity()).append("</td>");
            html.append("<td style='padding: 10px; text-align: right; border: 1px solid #ddd;'>").append(item.getPrice()).append(" TL</td>");
            html.append("</tr>");
        }

        html.append("<tr style='font-weight: bold; background-color: #4CAF50; color: white;'>");
        html.append("<td colspan='2' style='padding: 10px; border: 1px solid #ddd;'>TOPLAM</td>");
        html.append("<td style='padding: 10px; text-align: right; border: 1px solid #ddd;'>").append(orderEvent.getTotalAmount()).append(" TL</td>");
        html.append("</tr>");
        html.append("</table>");
        html.append("<p style='margin-top: 20px;'>Siparişiniz en kısa sürede kargoya verilecektir.</p>");
        html.append("<p style='color: #888; font-size: 12px;'>Bu otomatik bir emaildir.</p>");
        html.append("</div></body></html>");

        return html.toString();
    }
}