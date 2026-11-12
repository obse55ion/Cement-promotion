package org.example.entity;

import java.time.LocalDateTime;

public class Order {

    private int discount = 50;
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
        setQuantity(quantity);
        setOrderTime(orderTime);
        setPrice(price);
        setCustomerName(customerName);
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

    public void setOrderTime(LocalDateTime orderTime) {
        if(orderTime == null) {
            throw new IllegalArgumentException("Время заказа не может быть пустым");
        }
        this.orderTime = orderTime;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Колличество не может быть null");
        }
        this.quantity = quantity;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null) {
            throw new IllegalArgumentException("Заказчик не может быть null");
        }
        this.customerName = customerName;
    }

    public void setPrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Цена не может быть пустой");
        }
        this.price = price;
    }

}
