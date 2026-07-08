package com.prashant.restaurantpos.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.restaurantpos.order.entity.Order;
import com.prashant.restaurantpos.order.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
    long countByStatusNot(OrderStatus status);
    long count();

}
