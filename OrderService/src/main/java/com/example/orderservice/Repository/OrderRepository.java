package com.example.orderservice.Repository;

import com.example.orderservice.Model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    List<OrderModel> findByUserId(Long userId);
}
