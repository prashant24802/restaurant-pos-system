package com.prashant.restaurantpos.billing.dto;

import java.math.BigDecimal;
import java.util.List;

import com.prashant.restaurantpos.order.dto.OrderItemResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillResponse {

    private Long orderId;

    private Integer tableNumber;

    private List<OrderItemResponse> items;

    private BigDecimal subtotal;

    private BigDecimal tax;

    private BigDecimal grandTotal;
}