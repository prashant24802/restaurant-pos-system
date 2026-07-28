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
    public DashboardResponse getSummary() {

        long todayOrders = orderRepository.count();

        long pendingBills = orderRepository.countByStatusNot(OrderStatus.PAID);

        long paidBills = todayOrders - pendingBills;

        long availableTables =
                tableRepository.countByStatus(TableStatus.AVAILABLE);

        long occupiedTables =
                tableRepository.countByStatus(TableStatus.OCCUPIED);

        BigDecimal todayRevenue =
                orderRepository.findAll()
                        .stream()
                        .filter(order -> order.getStatus() == OrderStatus.PAID)
                        .map(Order::getTotalAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardResponse.builder()
                .todayRevenue(todayRevenue)
                .todayOrders(todayOrders)
                .paidBills(paidBills)
                .pendingBills(pendingBills)
                .availableTables(availableTables)
                .occupiedTables(occupiedTables)
                .totalMenuItems(0L) // Replace with menuRepository.count() later
                .build();
    }
}