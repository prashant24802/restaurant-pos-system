package com.prashant.restaurantpos.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prashant.restaurantpos.order.dto.AddOrderItemRequest;
import com.prashant.restaurantpos.order.dto.CreateOrderRequest;
import com.prashant.restaurantpos.order.dto.OrderResponse;
import com.prashant.restaurantpos.order.service.OrderService;
import java.util.List;

import com.prashant.restaurantpos.order.entity.OrderStatus;

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

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PatchMapping("/{orderId}/status")
    public OrderResponse updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        return orderService.updateStatus(orderId, status);
    }
}