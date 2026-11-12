package org.example.entity;

import java.time.LocalDateTime;

public class Order {

    private int discount;
    private int quantity;
    private LocalDateTime orderTime;
    private int price;
    private String customerName;

    public Order(int discount,
                 int quantity,
                 LocalDateTime orderTime,
                 int price,
                 String customerName) {
        this.discount = discount;
        this.quantity = quantity;
        this.orderTime = orderTime;
        this.price = price;
        this.customerName = customerName;
    }

    public int getDiscount() {
        return discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public int getPrice() {
        return price;
    }

    public String getCustomerName() {
        return customerName;
    }
}
