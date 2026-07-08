package com.prashant.restaurantpos.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.prashant.restaurantpos.table.entity.RestaurantTable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {

        status = OrderStatus.PENDING;
        totalAmount = BigDecimal.ZERO;
        createdAt = LocalDateTime.now();
    }
}
