package com.prashant.restaurantpos.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prashant.restaurantpos.order.dto.AddOrderItemRequest;
import com.prashant.restaurantpos.order.dto.CreateOrderRequest;
import com.prashant.restaurantpos.order.dto.OrderResponse;
import com.prashant.restaurantpos.order.entity.OrderStatus;

public interface OrderService {

    OrderResponse createOrder(
            CreateOrderRequest request);

    OrderResponse addItem(
            Long orderId,
            AddOrderItemRequest request);

    Page<OrderResponse> getPage(
            String search,
            OrderStatus status,
            Pageable pageable);

    OrderResponse getOrderById(
            Long id);

    OrderResponse updateStatus(
            Long orderId,
            OrderStatus status);

    OrderResponse removeItem(
            Long orderId,
            Long orderItemId);

    OrderResponse cancelOrder(
            Long orderId);

}