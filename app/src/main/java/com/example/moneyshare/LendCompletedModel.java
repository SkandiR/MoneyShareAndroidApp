package com.example.moneyshare;

public class LendCompletedModel {
    private Long amount;
    private Double offeredROI;
    private String user_id;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getOfferedROI() {
        return offeredROI;
    }

    public void setOfferedROI(Double offeredROI) {
        this.offeredROI = offeredROI;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LendCompletedModel(Long amount, Double offeredROI, String user_id) {
        this.amount = amount;
        this.offeredROI = offeredROI;
        this.user_id = user_id;
    }
}
