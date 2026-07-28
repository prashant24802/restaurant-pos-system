package com.prashant.restaurantpos.billing.controller;

import com.prashant.restaurantpos.billing.dto.BillResponse;
import com.prashant.restaurantpos.billing.dto.PaymentRequest;
import com.prashant.restaurantpos.billing.enums.PaymentStatus;
import com.prashant.restaurantpos.billing.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
@CrossOrigin
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/order/{orderId}")
    public BillResponse generateBill(@PathVariable Long orderId) {
        return billingService.generateBill(orderId);
    }

    @GetMapping
    public List<BillResponse> getAllBills() {
        return billingService.getAllBills();
    }

    @GetMapping("/{billId}")
    public BillResponse getBill(@PathVariable Long billId) {
        return billingService.getBill(billId);
    }

    @GetMapping("/order/{orderId}")
    public BillResponse getBillByOrder(@PathVariable Long orderId) {
        return billingService.getBillByOrder(orderId);
    }

    @GetMapping("/invoice/{invoiceNumber}")
    public BillResponse getBillByInvoice(
            @PathVariable String invoiceNumber) {
        return billingService.getBillByInvoice(invoiceNumber);
    }

    @GetMapping("/status/{status}")
    public List<BillResponse> getBillsByStatus(
            @PathVariable PaymentStatus status) {
        return billingService.getBillsByStatus(status);
    }

    @GetMapping("/today")
    public List<BillResponse> getTodayBills() {
        return billingService.getTodayBills();
    }

    @PatchMapping("/{billId}/pay")
    public BillResponse makePayment(
            @PathVariable Long billId,
            @Valid @RequestBody PaymentRequest request) {

        return billingService.makePayment(billId, request);
    }
}