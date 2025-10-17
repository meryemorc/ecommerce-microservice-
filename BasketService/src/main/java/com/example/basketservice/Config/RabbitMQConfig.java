package com.example.basketservice.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange adı sipariş dağıtım merkezi gibi bir şey
    public static final String ORDER_EXCHANGE = "order-exchange";

    // mesajları tutan kuyruk
    public static final String ORDER_QUEUE = "order-queue";

    // mesajların etiketi
    public static final String ORDER_ROUTING_KEY = "order.completed";

    // Exchange oluştur (Topic Exchange)
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    // true kuyruk rstart olsa bile kuyruk silinmez demek
    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE, true);  // durable = true (kalıcı)
    }

    // Exchange ve Queue'yu bağla (Binding)
    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange orderExchange) {
        return BindingBuilder
                .bind(orderQueue)
                .to(orderExchange)
                .with(ORDER_ROUTING_KEY);
    }

    // Message Converter (JSON)
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate (Mesaj göndermek için)
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
