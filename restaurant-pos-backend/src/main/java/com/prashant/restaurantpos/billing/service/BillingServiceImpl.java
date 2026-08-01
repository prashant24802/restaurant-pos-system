package com.prashant.restaurantpos.billing.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prashant.restaurantpos.billing.dto.BillItemResponse;
import com.prashant.restaurantpos.billing.dto.BillResponse;
import com.prashant.restaurantpos.billing.dto.PaymentRequest;
import com.prashant.restaurantpos.billing.entity.Bill;
import com.prashant.restaurantpos.billing.enums.PaymentStatus;
import com.prashant.restaurantpos.billing.repository.BillRepository;
import com.prashant.restaurantpos.exception.ResourceNotFoundException;
import com.prashant.restaurantpos.order.entity.Order;
import com.prashant.restaurantpos.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillRepository billRepository;
    private final OrderRepository orderRepository;
    private final InvoiceNumberGenerator invoiceNumberGenerator;

    @Override
    public BillResponse generateBill(Long orderId) {

        if (billRepository.findByOrderId(orderId).isPresent()) {
            throw new IllegalStateException("Bill already exists for this order.");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        Bill bill = Bill.builder()
                .invoiceNumber(invoiceNumberGenerator.generate())
                .order(order)
                .subtotal(order.getSubtotal())
                .tax(order.getTax())
                .discount(java.math.BigDecimal.ZERO)
                .totalAmount(order.getTotalAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        Bill savedBill = billRepository.save(bill);

        return toResponse(savedBill);
    }

    private BillResponse toResponse(Bill bill) {

    List<BillItemResponse> items = bill.getOrder()
            .getItems()
            .stream()
            .map(item -> BillItemResponse.builder()
                    .itemName(item.getMenuItem().getName())
                    .quantity(item.getQuantity())
                    .unitPrice(item.getPrice())
                    .total(
                            item.getPrice().multiply(
                                    BigDecimal.valueOf(item.getQuantity())
                            )
                    )
                    .build())
            .toList();

    return BillResponse.builder()
            .id(bill.getId())
            .invoiceNumber(bill.getInvoiceNumber())
            .orderId(bill.getOrder().getId())
            .tableNumber(
                    String.valueOf(
                            bill.getOrder()
                                    .getTable()
                                    .getTableNumber()))
            .subtotal(bill.getSubtotal())
            .tax(bill.getTax())
            .discount(bill.getDiscount())
            .totalAmount(bill.getTotalAmount())
            .paymentMethod(bill.getPaymentMethod())
            .paymentStatus(bill.getPaymentStatus())
            .billedAt(bill.getBilledAt())
            .paidAt(bill.getPaidAt())
            .items(items)
            .build();
}
    @Override
public BillResponse getBill(Long billId) {

    Bill bill = billRepository.findById(billId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Bill not found"));

    return toResponse(bill);
}

@Override
public List<BillResponse> getAllBills() {

    return billRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
}


@Override
public BillResponse getBillByOrder(Long orderId) {

    Bill bill = billRepository.findByOrderId(orderId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Bill not found for order"));

    return toResponse(bill);
}

@Override
public BillResponse getBillByInvoice(String invoiceNumber) {

    Bill bill = billRepository.findByInvoiceNumber(invoiceNumber)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Invoice not found"));

    return toResponse(bill);
}

@Override
public List<BillResponse> getBillsByStatus(PaymentStatus status) {

    return billRepository.findByPaymentStatus(status)
            .stream()
            .map(this::toResponse)
            .toList();
}

@Override
public List<BillResponse> getTodayBills() {

    LocalDateTime start = LocalDate.now().atStartOfDay();
    LocalDateTime end = start.plusDays(1);

    return billRepository.findByBilledAtBetween(start, end)
            .stream()
            .map(this::toResponse)
            .toList();
}

@Override
public BillResponse makePayment(Long billId,
                                PaymentRequest request) {

    Bill bill = billRepository.findById(billId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Bill not found"));

    bill.setPaymentMethod(request.getPaymentMethod());
    bill.setPaymentStatus(PaymentStatus.PAID);
    bill.setPaidAt(java.time.LocalDateTime.now());

    Bill savedBill = billRepository.save(bill);

    return toResponse(savedBill);
 }
}