package com.example.orderservice.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.basket}")
    private String basketQueue;

    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.exchange.order}")
    private String orderExchange;

    @Value("${rabbitmq.routing.key.basket}")
    private String basketRoutingKey;

    @Value("${rabbitmq.routing.key.notification}")
    private String notificationRoutingKey;

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    @Bean
    public Queue basketQueue() {
        return new Queue(basketQueue, true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }

    @Bean
    public Binding basketBinding() {
        return BindingBuilder.bind(basketQueue()).to(orderExchange()).with(basketRoutingKey);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(orderExchange()).with(notificationRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}