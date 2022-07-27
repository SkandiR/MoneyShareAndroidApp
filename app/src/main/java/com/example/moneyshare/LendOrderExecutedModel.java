package com.example.moneyshare;

public class LendOrderExecutedModel {
    private Long amount;
    private double RoI, credit_score;
    private String user_name;

    public LendOrderExecutedModel() {

    }
    public LendOrderExecutedModel(Long amount, double roI, double credit_score, String name) {
        this.amount = amount;
        this.RoI = roI;
        this.credit_score = credit_score;
        this.user_name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public double getRoI() {
        return RoI;
    }

    public void setRoI(double roI) {
        RoI = roI;
    }

    public double getCredit_score() {
        return credit_score;
    }

    public void setCredit_score(double credit_score) {
        this.credit_score = credit_score;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
