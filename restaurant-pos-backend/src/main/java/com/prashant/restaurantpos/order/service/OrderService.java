package com.prashant.restaurantpos.order.service;

import java.util.List;

import com.prashant.restaurantpos.order.dto.AddOrderItemRequest;
import com.prashant.restaurantpos.order.dto.CreateOrderRequest;
import com.prashant.restaurantpos.order.dto.OrderResponse;
import com.prashant.restaurantpos.order.entity.OrderStatus;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse addItem(Long orderId, AddOrderItemRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse updateStatus(Long orderId, OrderStatus status);

}