package com.prashant.restaurantpos.order.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.prashant.restaurantpos.menu.entity.MenuItem;
import com.prashant.restaurantpos.menu.repository.MenuItemRepository;
import com.prashant.restaurantpos.order.dto.AddOrderItemRequest;
import com.prashant.restaurantpos.order.dto.CreateOrderRequest;
import com.prashant.restaurantpos.order.dto.OrderResponse;
import com.prashant.restaurantpos.order.entity.Order;
import com.prashant.restaurantpos.order.entity.OrderItem;
import com.prashant.restaurantpos.order.repository.OrderItemRepository;
import com.prashant.restaurantpos.order.repository.OrderRepository;
import com.prashant.restaurantpos.table.entity.RestaurantTable;
import com.prashant.restaurantpos.table.entity.TableStatus;
import com.prashant.restaurantpos.table.repository.RestaurantTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestaurantTableRepository tableRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        RestaurantTable table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        table.setStatus(TableStatus.OCCUPIED);
        tableRepository.save(table);

        Order order = Order.builder()
                .table(table)
                .build();

        order = orderRepository.save(order);

        return mapToResponse(order);
    }

    @Override
    public OrderResponse addItem(Long orderId, AddOrderItemRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        BigDecimal subtotal =
                menuItem.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        OrderItem item = OrderItem.builder()
                .order(order)
                .menuItem(menuItem)
                .quantity(request.getQuantity())
                .price(menuItem.getPrice())
                .subtotal(subtotal)
                .build();

        orderItemRepository.save(item);

        order.setTotalAmount(order.getTotalAmount().add(subtotal));

        orderRepository.save(order);

        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .tableNumber(order.getTable().getTableNumber())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .build();
    }
}