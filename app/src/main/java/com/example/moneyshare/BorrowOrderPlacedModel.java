package com.example.moneyshare;

public class BorrowOrderPlacedModel {
    private Long amount;
    private double preferredRoI;

    public BorrowOrderPlacedModel(Long amount, double preferredRoI) {
        this.amount = amount;
        this.preferredRoI = preferredRoI;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public double getPreferredRoI() {
        return preferredRoI;
    }

    public void setPreferredRoI(double preferredRoI) {
        this.preferredRoI = preferredRoI;
    }
}
