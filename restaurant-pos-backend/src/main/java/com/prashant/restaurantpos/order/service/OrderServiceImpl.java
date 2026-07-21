package com.prashant.restaurantpos.order.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prashant.restaurantpos.menu.entity.MenuItem;
import com.prashant.restaurantpos.menu.repository.MenuItemRepository;
import com.prashant.restaurantpos.order.dto.AddOrderItemRequest;
import com.prashant.restaurantpos.order.dto.CreateOrderRequest;
import com.prashant.restaurantpos.order.dto.OrderItemResponse;
import com.prashant.restaurantpos.order.dto.OrderResponse;
import com.prashant.restaurantpos.order.entity.Order;
import com.prashant.restaurantpos.order.entity.OrderItem;
import com.prashant.restaurantpos.order.entity.OrderStatus;
import com.prashant.restaurantpos.order.repository.OrderItemRepository;
import com.prashant.restaurantpos.order.repository.OrderRepository;
import com.prashant.restaurantpos.order.specification.OrderSpecification;
import com.prashant.restaurantpos.table.entity.RestaurantTable;
import com.prashant.restaurantpos.table.entity.TableStatus;
import com.prashant.restaurantpos.table.repository.RestaurantTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final BigDecimal TAX_RATE =
            new BigDecimal("0.18");

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final RestaurantTableRepository tableRepository;

    private final MenuItemRepository menuItemRepository;

    @Override
    public OrderResponse createOrder(
            CreateOrderRequest request) {

        RestaurantTable table =
                tableRepository.findById(
                        request.getTableId())

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Table not found"));

        if (table.getStatus() != TableStatus.AVAILABLE) {

            throw new RuntimeException(
                    "Table is not available");

        }

        if (orderRepository.existsByTableIdAndStatusNot(
                table.getId(),
                OrderStatus.PAID)) {

            throw new RuntimeException(
                    "An active order already exists for this table");

        }

        table.setStatus(TableStatus.OCCUPIED);

        tableRepository.save(table);

        Order order = Order.builder()

                .table(table)

                .status(OrderStatus.PENDING)

                .subtotal(BigDecimal.ZERO)

                .tax(BigDecimal.ZERO)

                .totalAmount(BigDecimal.ZERO)

                .build();

        orderRepository.save(order);

        return mapToResponse(order);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getPage(

            String search,

            OrderStatus status,

            Pageable pageable) {

        return orderRepository.findAll(

                OrderSpecification.filter(
                        search,
                        status),

                pageable)

                .map(this::mapToResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(
            Long id) {

        Order order =
                orderRepository.findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"));

        return mapToResponse(order);

    }

        @Override
    public OrderResponse addItem(
            Long orderId,
            AddOrderItemRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        if (order.getStatus() == OrderStatus.PAID
                || order.getStatus() == OrderStatus.CANCELLED) {

            throw new RuntimeException(
                    "Cannot modify this order");

        }

        MenuItem menuItem =
                menuItemRepository.findById(
                        request.getMenuItemId())

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Menu item not found"));

        if (!menuItem.isAvailable()) {

            throw new RuntimeException(
                    "Menu item is unavailable");

        }

        OrderItem item = OrderItem.builder()

                .order(order)

                .menuItem(menuItem)

                .quantity(request.getQuantity())

                .price(menuItem.getPrice())

                .build();

        orderItemRepository.save(item);

        calculateTotals(order);

        orderRepository.save(order);

        return mapToResponse(order);

    }

    @Override
    public OrderResponse removeItem(
            Long orderId,
            Long orderItemId) {

        Order order =
                orderRepository.findById(orderId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"));

        OrderItem item =
                orderItemRepository.findById(orderItemId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order item not found"));

        if (!item.getOrder().getId().equals(orderId)) {

            throw new RuntimeException(
                    "Order item does not belong to this order");

        }

        orderItemRepository.delete(item);

        calculateTotals(order);

        orderRepository.save(order);

        return mapToResponse(order);

    }

    @Override
    public OrderResponse updateStatus(
            Long orderId,
            OrderStatus status) {

        Order order =
                orderRepository.findById(orderId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"));

        order.setStatus(status);

        if (status == OrderStatus.PAID
                || status == OrderStatus.CANCELLED) {

            RestaurantTable table =
                    order.getTable();

            table.setStatus(
                    TableStatus.AVAILABLE);

            tableRepository.save(table);

        }

        orderRepository.save(order);

        return mapToResponse(order);

    }

    @Override
    public OrderResponse cancelOrder(
            Long orderId) {

        return updateStatus(
                orderId,
                OrderStatus.CANCELLED);

    }

        private void calculateTotals(
            Order order) {

        List<OrderItem> items =
                orderItemRepository.findByOrderId(
                        order.getId());

        BigDecimal subtotal = items.stream()

                .map(OrderItem::getSubtotal)

                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);

        BigDecimal tax = subtotal
                .multiply(TAX_RATE)
                .setScale(
                        2,
                        RoundingMode.HALF_UP);

        BigDecimal total = subtotal.add(tax);

        order.setSubtotal(subtotal);

        order.setTax(tax);

        order.setTotalAmount(total);

    }

    private OrderResponse mapToResponse(
            Order order) {

        List<OrderItemResponse> items =
                orderItemRepository.findByOrderId(
                        order.getId())

                        .stream()

                        .map(item -> OrderItemResponse.builder()

                                .id(item.getId())

                                .menuItemId(
                                        item.getMenuItem().getId())

                                .menuItemName(
                                        item.getMenuItem().getName())

                                .quantity(
                                        item.getQuantity())

                                .price(
                                        item.getPrice())

                                .subtotal(
                                        item.getSubtotal())

                                .build())

                        .toList();

        return OrderResponse.builder()

                .id(order.getId())

                .tableId(
                        order.getTable().getId())

                .tableNumber(
                        order.getTable().getTableNumber())

                .status(
                        order.getStatus())

                .subtotal(
                        order.getSubtotal())

                .tax(
                        order.getTax())

                .totalAmount(
                        order.getTotalAmount())

                .createdAt(
                        order.getCreatedAt())

                .items(items)

                .build();

    }

}