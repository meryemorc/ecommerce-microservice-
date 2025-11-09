package com.example.notificationservice.Config;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";
    public static final String ORDER_NOTIFICATION_QUEUE = "order-notification-queue";

    public static final String NOTIFICATION_ORDER_PLACED_KEY = "notification.order.placed";

    @Bean
    public TopicExchange notificationExchange() {
        // Exchange, mesajların geldiği dağıtım noktasıdır.
        return new TopicExchange(NOTIFICATION_EXCHANGE, true, false); // Durable: true (kalıcı)
    }

    @Bean
    public Queue orderNotificationQueue() {
        // Queue, Notification Service'in mesajları okuyacağı yerdir.
        return new Queue(ORDER_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Binding binding(Queue orderNotificationQueue, TopicExchange notificationExchange) {
        // Binding, Exchange'i Kuyruğa, Routing Key üzerinden bağlar.
        return BindingBuilder
                .bind(orderNotificationQueue)
                .to(notificationExchange)
                .with(NOTIFICATION_ORDER_PLACED_KEY);
    }


    @Bean
    public MessageConverter messageConverter() {
        // Basket Service'ten JSON olarak gelen mesajları Java nesnesine (OrderPlacedEvent) çevirmek için gereklidir.
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        // Bu Bean, eğer Notification Service de bir yere mesaj gönderecek olsaydı kullanılırdı.
        // Mesaj alıcıları için şart değildir, ancak genel konfigürasyon için bırakılması iyidir.
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}

