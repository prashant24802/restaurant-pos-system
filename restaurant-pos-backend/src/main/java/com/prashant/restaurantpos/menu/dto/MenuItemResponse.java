package com.prashant.restaurantpos.menu.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuItemResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private boolean available;

    private String imageUrl;

    private Long categoryId;

    private String categoryName;
}
