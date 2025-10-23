package com.example.notificationservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="notificationdb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private String id;
    private Long userId;
    private String orderId;
    private String recipientEmail;
    private String message;
    private String errorMessage;
    private boolean sent;
    @CreatedDate
    private LocalDateTime createdAt;
    private String userEmail;
    private LocalDateTime sentAt;
}
