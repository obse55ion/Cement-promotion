package org.example.service;

import org.example.entity.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleService {

    private final BigDecimal price;
    private final BigDecimal startDiscount;
    private final BigDecimal discountStep;

    public SaleService(BigDecimal price, BigDecimal startDiscount, BigDecimal discountStep) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }
        if (startDiscount == null
                || startDiscount.compareTo(BigDecimal.ZERO) < 0
                || startDiscount.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException();
        }
        if (discountStep == null
                || discountStep.compareTo(BigDecimal.ZERO) <= 0
                || discountStep.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException();
        }
        if (startDiscount.add(discountStep).compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException();
        }

        this.price = price;
        this.startDiscount = startDiscount;
        this.discountStep = discountStep;
    }

    public Map<String, BigDecimal> processOrder(List<Order> orders) {
        Map<String, BigDecimal> companyTotals = new HashMap<>();
        orders.sort(Comparator.comparing(Order::getOrderTime));
        BigDecimal currentDiscount = startDiscount;

        for (Order order : orders) {
            BigDecimal defaultPrice = price.multiply(
                    BigDecimal.valueOf(order.getQuantity())
            );
            BigDecimal discountPrice = defaultPrice
                    .multiply(BigDecimal.ONE.subtract(currentDiscount))
                    .setScale(2, RoundingMode.HALF_UP);

            String companyName = order.getCustomerName();

            companyTotals.merge(companyName, discountPrice, BigDecimal::add);

            currentDiscount = currentDiscount.subtract(discountStep)
                    .max(BigDecimal.ZERO);
        }
        return companyTotals;
    }
}
