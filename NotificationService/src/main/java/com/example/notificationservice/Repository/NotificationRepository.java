package com.example.notificationservice.Repository;

import com.example.notificationservice.Model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends MongoRepository<Notification,String> {

    List<Notification> findByUserId(Long userId);

    Optional<Notification> findByOrderId(Long orderId);
}
