package com.prashant.restaurantpos.billing.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.restaurantpos.billing.entity.Bill;
import com.prashant.restaurantpos.billing.enums.PaymentStatus;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findByOrderId(Long orderId);

    Optional<Bill> findByInvoiceNumber(String invoiceNumber);

    List<Bill> findByPaymentStatus(PaymentStatus paymentStatus);

    List<Bill> findByBilledAtBetween(LocalDateTime start, LocalDateTime end);

}