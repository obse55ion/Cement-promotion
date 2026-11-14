package org.example.service;

import org.example.entity.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleService {

    private final int price;
    private final int startDiscount;
    private final int discountStep;

    public SaleService(int price, int startDiscount, int discountStep) {
        this.price = price;
        this.startDiscount = startDiscount;
        this.discountStep = discountStep;
    }

    public Map<String, Integer> processOrder(List<Order> orders) {
        Map<String, Integer> companyTotals = new HashMap<>();

        int currentDiscount = startDiscount;
        for (Order order : orders) {
            int defaultPrice = order.getQuantity() * price;
            int discountPrice = defaultPrice * (100 - currentDiscount) / 100;
            String companyName = order.getCustomerName();
            if (companyTotals.containsKey(companyName)) {
                companyTotals.put(companyName, companyTotals.get(companyName) + discountPrice);
            } else {
                companyTotals.put(companyName, discountPrice);
            }
            if (currentDiscount >= discountStep) {
                currentDiscount -= discountStep;
            } else {
                currentDiscount = 0;
            }
        }
        for (Map.Entry<String, Integer> entry : companyTotals.entrySet()) {
            String companyName = entry.getKey();
            Integer totalPrice = entry.getValue();
            System.out.println(companyName + " - " + totalPrice);
        }

        return companyTotals;

    }
}
