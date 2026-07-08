package com.prashant.restaurantpos.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prashant.restaurantpos.order.dto.AddOrderItemRequest;
import com.prashant.restaurantpos.order.dto.CreateOrderRequest;
import com.prashant.restaurantpos.order.dto.OrderResponse;
import com.prashant.restaurantpos.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        return orderService.createOrder(request);
    }

    @PostMapping("/{orderId}/items")
    public OrderResponse addItem(
            @PathVariable Long orderId,
            @Valid @RequestBody AddOrderItemRequest request) {

        return orderService.addItem(orderId, request);
    }
}