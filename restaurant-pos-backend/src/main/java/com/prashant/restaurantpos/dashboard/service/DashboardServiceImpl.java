package com.prashant.restaurantpos.dashboard.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.prashant.restaurantpos.dashboard.dto.DashboardResponse;
import com.prashant.restaurantpos.order.entity.Order;
import com.prashant.restaurantpos.order.entity.OrderStatus;
import com.prashant.restaurantpos.order.repository.OrderRepository;
import com.prashant.restaurantpos.table.entity.TableStatus;
import com.prashant.restaurantpos.table.repository.RestaurantTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final OrderRepository orderRepository;
    private final RestaurantTableRepository tableRepository;

    @Override
    public DashboardResponse getDashboard() {

        long totalOrders = orderRepository.count();

        long activeOrders =
                orderRepository.countByStatusNot(OrderStatus.PAID);

        long availableTables =
                tableRepository.countByStatus(TableStatus.AVAILABLE);

        long occupiedTables =
                tableRepository.countByStatus(TableStatus.OCCUPIED);

        BigDecimal totalRevenue =
                orderRepository.findAll()
                        .stream()
                        .filter(order -> order.getStatus() == OrderStatus.PAID)
                        .map(Order::getTotalAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardResponse.builder()
                .totalOrders(totalOrders)
                .activeOrders(activeOrders)
                .availableTables(availableTables)
                .occupiedTables(occupiedTables)
                .totalRevenue(totalRevenue)
                .build();
    }
}