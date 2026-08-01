package com.prashant.restaurantpos.billing.dto;

import java.math.BigDecimal;

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
public class BillItemResponse {

    private String itemName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal total;

}