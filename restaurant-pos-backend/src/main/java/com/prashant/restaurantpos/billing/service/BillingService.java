package com.prashant.restaurantpos.billing.service;

import com.prashant.restaurantpos.billing.dto.BillResponse;

public interface BillingService {

    BillResponse generateBill(Long orderId);

}
