package com.prashant.restaurantpos.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.prashant.restaurantpos.order.entity.Order;
import com.prashant.restaurantpos.order.entity.OrderStatus;

public interface OrderRepository
        extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    long count();

    long countByStatusNot(OrderStatus status);

    boolean existsByTableIdAndStatusNot(
            Long tableId,
            OrderStatus status);

    Optional<Order> findByTableIdAndStatusNot(
            Long tableId,
            OrderStatus status);

}