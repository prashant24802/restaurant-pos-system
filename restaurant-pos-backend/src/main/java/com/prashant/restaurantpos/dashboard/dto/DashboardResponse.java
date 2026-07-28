package com.prashant.restaurantpos.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private BigDecimal todayRevenue;

    private Long todayOrders;

    private Long paidBills;

    private Long pendingBills;

    private Long availableTables;

    private Long occupiedTables;

    private Long totalMenuItems;

}