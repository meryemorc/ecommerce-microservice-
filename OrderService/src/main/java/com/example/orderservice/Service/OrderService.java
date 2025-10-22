package com.example.orderservice.Service;

import com.example.orderservice.Dto.BasketCheckoutEvent;
import com.example.orderservice.Dto.OrderEvent;
import com.example.orderservice.Dto.OrderItemDto;
import com.example.orderservice.Messaging.OrderEventPublisher;
import com.example.orderservice.Model.OrderItem;
import com.example.orderservice.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.orderservice.Model.OrderModel;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Long.sum;
import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher publisher;

    public OrderModel createOrder(BasketCheckoutEvent event) {

        double totalAmount = event.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        OrderModel order = new OrderModel();//order oluştur
        order.setUserId(event.getUserId());
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);


        List<OrderItem> orderItems = event.getItems().stream()//order ıtem olustur
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(item.getProductId());
                    orderItem.setProductName(item.getProductName());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(item.getPrice());
                    orderItem.setOrder(order);  // her ıtem hangı ordera baglı
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);

        OrderModel savedOrder = orderRepository.save(order);

        //basketdto daki ıtemleri order dto ıteme donusturur
        List<OrderItemDto> orderItemDto = event.getItems().stream()
                .map(item -> new OrderItemDto(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());

        OrderEvent orderEvent = new OrderEvent( //publisherla gidecek paket
                savedOrder.getId(),
                savedOrder.getUserId(),
                event.getUsername(),
                orderItemDto,
                savedOrder.getTotalAmount(),
                savedOrder.getOrderDate()
        );

        // NotificationService'e gönder
        publisher.publishOrderEvent(orderEvent);
        return savedOrder;
    }
}
