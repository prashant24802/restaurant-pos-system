package com.prashant.restaurantpos.order.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prashant.restaurantpos.order.dto.AddOrderItemRequest;
import com.prashant.restaurantpos.order.dto.CreateOrderRequest;
import com.prashant.restaurantpos.order.dto.OrderResponse;
import com.prashant.restaurantpos.order.entity.OrderStatus;
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

            @Valid
            @RequestBody AddOrderItemRequest request) {

        return orderService.addItem(

                orderId,

                request

        );

    }

    @GetMapping
    public Page<OrderResponse> getOrders(

            @RequestParam(required = false)
            String search,

            @RequestParam(required = false)
            OrderStatus status,

            Pageable pageable) {

        return orderService.getPage(

                search,

                status,

                pageable

        );

    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(
            @PathVariable Long id) {

        return orderService.getOrderById(id);

    }

    @PatchMapping("/{orderId}/status")
    public OrderResponse updateStatus(

            @PathVariable Long orderId,

            @RequestParam
            OrderStatus status) {

        return orderService.updateStatus(

                orderId,

                status

        );

    }

    @DeleteMapping("/{orderId}/items/{orderItemId}")
    public OrderResponse removeItem(

            @PathVariable Long orderId,

            @PathVariable Long orderItemId) {

        return orderService.removeItem(

                orderId,

                orderItemId

        );

    }

    @PatchMapping("/{orderId}/cancel")
    public OrderResponse cancelOrder(
            @PathVariable Long orderId) {

        return orderService.cancelOrder(orderId);

    }

}