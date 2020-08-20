package com.example.multipledatabaseconnect.repository.dbo;

import com.example.multipledatabaseconnect.entity.dbo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
