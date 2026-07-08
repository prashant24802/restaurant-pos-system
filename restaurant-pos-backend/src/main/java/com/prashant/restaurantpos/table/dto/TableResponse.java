package com.prashant.restaurantpos.table.dto;


import com.prashant.restaurantpos.table.entity.TableStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableResponse {

    private Long id;
    private Integer tableNumber;
    private Integer capacity;
    private TableStatus status;
}