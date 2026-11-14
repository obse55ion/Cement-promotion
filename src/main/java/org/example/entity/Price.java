package org.example.entity;

import org.example.policy.DiscountPolicy;

public class Price {

    private final int price;
    private final DiscountPolicy discountPolicy;


    public Price(DiscountPolicy policy, int price) {
        this.discountPolicy = policy;
        this.price = price;
    }

    public int getDiscountedPrice() {
        int discount = discountPolicy.getCurrentDiscount();
        return price * (100 - discount) / 100;
    }
}
