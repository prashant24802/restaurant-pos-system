package com.prashant.restaurantpos.billing.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.prashant.restaurantpos.billing.enums.PaymentMethod;
import com.prashant.restaurantpos.billing.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {

    private Long id;

    private String invoiceNumber;

    private Long orderId;

    private String tableNumber;

    private BigDecimal subtotal;

    private BigDecimal tax;

    private BigDecimal discount;

    private BigDecimal totalAmount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime billedAt;

    private LocalDateTime paidAt;

}