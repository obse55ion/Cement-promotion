package org.example.service;

import org.example.entity.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class SaleService {

    private final BigDecimal price;
    private final int startDiscount;
    private final int discountStep;

    public SaleService(BigDecimal price, int startDiscount, int discountStep) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Значение цена не может быть ноль или отрицательным");
        }
        if (startDiscount < 0 || startDiscount > 100) {
            throw new IllegalArgumentException("Скидка не может быть больше 100 или отрицательной");
        }
        if (discountStep <= 0 || discountStep > 100) {
            throw new IllegalArgumentException("Шаг уменьшения скидки от 1 до 100");
        }
        if (startDiscount + discountStep > 100) {
            throw new IllegalArgumentException("Сумма начальной скидки и шага не должна превышать 100%");
        }

        this.price = price;
        this.startDiscount = startDiscount;
        this.discountStep = discountStep;
    }

    public Map<String, BigDecimal> processOrder(List<Order> orders) {
        Map<String, BigDecimal> companyTotals = new HashMap<>();

        List<Order> sortedOrders = new ArrayList<>(orders);
        sortedOrders.sort(Comparator.comparing(Order::getOrderTime));
        int currentDiscount = startDiscount;

        for (Order order : orders) {
            BigDecimal defaultPrice = price.multiply(
                    BigDecimal.valueOf(order.getQuantity())
            );

            BigDecimal discountPrice = defaultPrice
                    .multiply(BigDecimal.valueOf(100 - currentDiscount))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            String companyName = order.getCustomerName();

            companyTotals.merge(companyName, discountPrice, BigDecimal::add);

            currentDiscount = Math.max(0, currentDiscount - discountStep);
        }
        return companyTotals;
    }
}
