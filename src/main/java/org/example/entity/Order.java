package org.example.entity;

import java.time.LocalDateTime;

public class Order {

    private final LocalDateTime orderTime;
    private final int quantity;
    private final String customerName;

    public Order(LocalDateTime orderTime,
                 int quantity,
                 String customerName) {
        this.orderTime = orderTime;
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
