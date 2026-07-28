package com.prashant.restaurantpos.billing.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.prashant.restaurantpos.billing.enums.PaymentMethod;
import com.prashant.restaurantpos.billing.enums.PaymentStatus;
import com.prashant.restaurantpos.order.entity.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal tax = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false, updatable = false)
    private LocalDateTime billedAt;

    @Column
    private LocalDateTime paidAt;

    @PrePersist
    public void prePersist() {

        billedAt = LocalDateTime.now();

        if (subtotal == null)
            subtotal = BigDecimal.ZERO;

        if (tax == null)
            tax = BigDecimal.ZERO;

        if (discount == null)
            discount = BigDecimal.ZERO;

        if (totalAmount == null)
            totalAmount = BigDecimal.ZERO;

        if (paymentStatus == null)
            paymentStatus = PaymentStatus.PENDING;
    }

}