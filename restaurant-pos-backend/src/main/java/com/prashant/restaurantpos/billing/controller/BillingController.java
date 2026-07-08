package com.prashant.restaurantpos.billing.controller;

import org.springframework.web.bind.annotation.*;

import com.prashant.restaurantpos.billing.dto.BillResponse;
import com.prashant.restaurantpos.billing.service.BillingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/orders/{orderId}")
    public BillResponse generateBill(
            @PathVariable Long orderId) {

        return billingService.generateBill(orderId);
    }
}