package com.prashant.restaurantpos.table.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TableRequest {

    @NotNull
    private Integer tableNumber;

    @NotNull
    @Min(1)
    private Integer capacity;
}
