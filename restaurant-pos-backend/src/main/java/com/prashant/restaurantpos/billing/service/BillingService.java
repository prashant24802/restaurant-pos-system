package com.prashant.restaurantpos.billing.service;

import com.prashant.restaurantpos.billing.dto.BillResponse;
import com.prashant.restaurantpos.billing.dto.PaymentRequest;
import com.prashant.restaurantpos.billing.enums.PaymentStatus;

import java.util.List;

public interface BillingService {

    BillResponse generateBill(Long orderId);

    BillResponse getBill(Long billId);

    List<BillResponse> getAllBills();

    BillResponse getBillByOrder(Long orderId);

    BillResponse getBillByInvoice(String invoiceNumber);

    List<BillResponse> getBillsByStatus(PaymentStatus status);

    List<BillResponse> getTodayBills();

    BillResponse makePayment(Long billId, PaymentRequest request);
}