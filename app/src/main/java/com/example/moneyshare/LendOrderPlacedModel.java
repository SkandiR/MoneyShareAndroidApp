package com.example.moneyshare;

import java.util.List;

public class LendOrderPlacedModel {
    private Long amount;
    private Double preferredCreditScore, offeredROI;
    private List<JsonData.BorrowDetails> borrowLists;
    private String lender_id;

    public String getLender_id() {
        return lender_id;
    }

    public void setLender_id(String lender_id) {
        this.lender_id = lender_id;
    }

    public LendOrderPlacedModel(Long amount, Double roi, Double creditScore, List<JsonData.BorrowDetails>borrowLists, String lender_id) {
        this.amount = amount;
        this.preferredCreditScore = creditScore;
        this.offeredROI = roi;
        this.borrowLists = borrowLists;
        this.lender_id = lender_id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getPreferredCreditScore() {
        return preferredCreditScore;
    }

    public void setPreferredCreditScore(Double preferredCreditScore) {
        this.preferredCreditScore = preferredCreditScore;
    }

    public Double getOfferedROI() {
        return offeredROI;
    }

    public void setOfferedROI(Double offeredROI) {
        this.offeredROI = offeredROI;
    }

    public List<JsonData.BorrowDetails> getBorrowLists() {
        return borrowLists;
    }

    public void setBorrowLists(List<JsonData.BorrowDetails> borrowLists) {
        this.borrowLists = borrowLists;
    }
}
