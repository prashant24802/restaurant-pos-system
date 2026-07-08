package com.prashant.restaurantpos.dashboard.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private long totalOrders;

    private long activeOrders;

    private long availableTables;

    private long occupiedTables;

    private BigDecimal totalRevenue;
}