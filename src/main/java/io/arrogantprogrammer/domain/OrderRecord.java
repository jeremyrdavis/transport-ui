package io.arrogantprogrammer.domain;

public record OrderRecord(Long id, String name, MenuItem menuItem, OrderStatus orderStatus, PaymentStatus paymentStatus) {
}
