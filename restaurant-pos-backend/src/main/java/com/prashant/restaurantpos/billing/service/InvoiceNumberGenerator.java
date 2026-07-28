package com.prashant.restaurantpos.billing.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

@Component
public class InvoiceNumberGenerator {

    private final AtomicLong counter = new AtomicLong(1);

    public synchronized String generate() {

        String date = LocalDate.now()
                .format(DateTimeFormatter.BASIC_ISO_DATE);

        return "INV-" + date + "-"
                + String.format("%06d", counter.getAndIncrement());
    }
}