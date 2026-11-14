package org.example.policy;

public class DiscountPolicy {

    private final int startDiscount;
    private final int stepDiscount;
    private final int midDiscount;
    private int currentDiscount;

    public DiscountPolicy(int startDiscount, int stepDiscount, int midDiscount, int currentDiscount) {
        this.startDiscount = startDiscount;
        this.stepDiscount = stepDiscount;
        this.midDiscount = midDiscount;
        this.currentDiscount = currentDiscount;
    }

    public int getCurrentDiscount() {
        return currentDiscount;
    }

    public void next() {
        currentDiscount = Math.max(midDiscount, currentDiscount - stepDiscount);
    }
}
