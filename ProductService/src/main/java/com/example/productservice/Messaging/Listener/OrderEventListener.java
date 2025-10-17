package com.example.productservice.Messaging.Listener;

import com.example.productservice.Model.OrderCompletedEvent;
import com.example.productservice.Service.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Data
@RequiredArgsConstructor
@Service
public class OrderEventListener {

    private final ProductService productService;


    @RabbitListener(queues = "order-queue")
    public void handleOrderCompleted(OrderCompletedEvent event){

        try{
            productService.decreaseStock(event.getProductId(), event.getQuantity());
            log.info("Stock decreased successfully for productId: {}", event.getProductId());
        } catch (Exception e) {
            log.error("Error while decreasing stock for productId: {}, error: {}",
                    event.getProductId(), e.getMessage());
        }
    }




}
