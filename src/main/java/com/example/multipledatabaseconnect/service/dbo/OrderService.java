package com.example.multipledatabaseconnect.service.dbo;

import com.example.multipledatabaseconnect.entity.dbo.Order;
import com.example.multipledatabaseconnect.repository.dbo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }
}
