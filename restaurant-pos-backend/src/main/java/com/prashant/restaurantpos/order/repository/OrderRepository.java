package com.prashant.restaurantpos.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.restaurantpos.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
