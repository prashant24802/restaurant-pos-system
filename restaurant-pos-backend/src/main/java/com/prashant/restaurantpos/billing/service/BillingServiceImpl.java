package com.prashant.restaurantpos.billing.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prashant.restaurantpos.billing.dto.BillResponse;
import com.prashant.restaurantpos.order.dto.OrderItemResponse;
import com.prashant.restaurantpos.order.entity.Order;
import com.prashant.restaurantpos.order.entity.OrderItem;
import com.prashant.restaurantpos.order.repository.OrderItemRepository;
import com.prashant.restaurantpos.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public BillResponse generateBill(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItem> orderItems =
                orderItemRepository.findByOrderId(orderId);

        List<OrderItemResponse> items = orderItems.stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .menuItemName(item.getMenuItem().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .toList();

        BigDecimal subtotal = order.getTotalAmount();

        BigDecimal tax = subtotal
                .multiply(new BigDecimal("0.18"))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal grandTotal = subtotal.add(tax);

        return BillResponse.builder()
                .orderId(order.getId())
                .tableNumber(order.getTable().getTableNumber())
                .items(items)
                .subtotal(subtotal)
                .tax(tax)
                .grandTotal(grandTotal)
                .build();
    }
}
