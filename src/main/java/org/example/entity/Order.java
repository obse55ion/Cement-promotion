package org.example.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private final LocalDateTime orderTime;
    private final int quantity;
    private final String customerName;

    public Order(LocalDateTime orderTime,
                 int quantity,
                 String customerName) {

        this.orderTime = Objects.requireNonNull(
                orderTime, "Время заказа не может быть null"
        );

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Количество цемента должно быть больше нуля"
            );
        }

        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException(
                    "Название компании не может быть пустым"
            );
        }
        this.quantity = quantity;
        this.customerName = customerName;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

}
