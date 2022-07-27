package com.example.moneyshare;

public class BorrowOrderExecutedModel {
    private Long Amount;
    private double preferredRoI;
    private String lender_id;
    private String id;

    public BorrowOrderExecutedModel(Long amount, double preferredRoI, String lender_id, String id) {
        Amount = amount;
        this.preferredRoI = preferredRoI;
        this.lender_id = lender_id;
        this.id = id;
    }

    public Long getAmount() {
        return Amount;
    }

    public void setAmount(Long amount) {
        Amount = amount;
    }

    public double getPreferredRoI() {
        return preferredRoI;
    }

    public void setPreferredRoI(double preferredRoI) {
        this.preferredRoI = preferredRoI;
    }

    public String getLender_id() {
        return lender_id;
    }

    public void setLender_id(String lender_id) {
        this.lender_id = lender_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
