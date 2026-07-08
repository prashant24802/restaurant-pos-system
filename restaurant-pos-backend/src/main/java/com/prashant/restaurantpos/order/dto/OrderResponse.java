package com.prashant.restaurantpos.order.dto;

import java.math.BigDecimal;

import com.prashant.restaurantpos.order.entity.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

    private Long id;

    private Integer tableNumber;

    private OrderStatus status;

    private BigDecimal totalAmount;
}
