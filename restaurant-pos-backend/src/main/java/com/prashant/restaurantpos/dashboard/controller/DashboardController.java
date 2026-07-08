package com.prashant.restaurantpos.dashboard.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.restaurantpos.dashboard.dto.DashboardResponse;
import com.prashant.restaurantpos.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponse getDashboard() {
        return dashboardService.getDashboard();
    }
}
