package com.example.shop.mock;

import com.example.shop.entity.CustomerOrder;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomerOrderTestDataBuilder {

    private Long id;
    private Double totalPrice;
    private LocalDateTime createdAt;

    public CustomerOrderTestDataBuilder() {
        this.id = 500L;
        this.totalPrice = 250.75D;
        this.createdAt = LocalDateTime.of(2026, 7, 29, 10, 30);
    }

    public CustomerOrderTestDataBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CustomerOrderTestDataBuilder withTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public CustomerOrderTestDataBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerOrder build() {
        CustomerOrder customerOrder = new CustomerOrder();
        applyField(customerOrder, "id", id);
        applyField(customerOrder, "totalPrice", totalPrice);
        applyField(customerOrder, "createdAt", createdAt);
        return customerOrder;
    }

    public static CustomerOrder validOrder() {
        return new CustomerOrderTestDataBuilder().build();
    }

    public static CustomerOrder boundaryOrder() {
        return new CustomerOrderTestDataBuilder()
                .withId(Long.MAX_VALUE)
                .withTotalPrice(Double.MAX_VALUE)
                .withCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
                .build();
    }

    public static CustomerOrder negativeTotalOrder() {
        return new CustomerOrderTestDataBuilder()
                .withId(501L)
                .withTotalPrice(-100.00D)
                .build();
    }

    public static CustomerOrder nullTotalOrder() {
        return new CustomerOrderTestDataBuilder()
                .withId(502L)
                .withTotalPrice(null)
                .withCreatedAt(null)
                .build();
    }

    public static List<CustomerOrder> validOrderList() {
        return Arrays.asList(validOrder(), boundaryOrder());
    }

    public static List<CustomerOrder> emptyOrderList() {
        return Collections.emptyList();
    }

    private static void applyField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to apply field value for " + fieldName, exception);
        }
    }
}
