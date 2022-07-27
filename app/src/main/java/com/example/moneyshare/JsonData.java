package com.example.moneyshare;

import java.util.List;

public class JsonData {

    public class IsNewUser {
        private boolean isNewUser;

        public boolean isisNewUser() {
            return isNewUser;
        }

        public void setisNewUser(boolean isNewUser) {
            this.isNewUser = isNewUser;
        }
    }

    public static class SaveUser {
        private String id;
        private String name;
        private String ssn;
        private double creditScore;

        public SaveUser(String id, String name, String ssn, double creditScore) {
            this.id = id;
            this.name = name;
            this.ssn = ssn;
            this.creditScore = creditScore;
        }



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSsn() {
            return ssn;
        }

        public void setSsn(String ssn) {
            this.ssn = ssn;
        }

        public double getCreditScore() {
            return creditScore;
        }

        public void setCreditScore(double creditScore) {
            this.creditScore = creditScore;
        }
    }

// getwalletapi

    public class WalletResponse {
        private Long totalAmount;
        private Long lentAmount;
        private Long borrowedAmount;

        public Long getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Long totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Long getLentAmount() {
            return lentAmount;
        }

        public void setLentAmount(Long lentAmount) {
            this.lentAmount = lentAmount;
        }

        public Long getBorrowedAmount() {
            return borrowedAmount;
        }

        public void setBorrowedAmount(Long borrowedAmount) {
            this.borrowedAmount = borrowedAmount;
        }
    }

    // add money
    public static class addMoney {  // POST
        String id;
        Long amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
    }

    // add Lend request
    public static class LendRequest {  //POST
        private String userId;
        private Long amount;
        private Double roi;
        private Double minCreditScore;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Double getRoi() {
            return roi;
        }

        public void setRoi(Double roi) {
            this.roi = roi;
        }

        public Double getMinCreditScore() {
            return minCreditScore;
        }

        public void setMinCreditScore(Double minCreditScore) {
            this.minCreditScore = minCreditScore;
        }
    }

    // add Borrow request
    public static class BorrowRequest { //POST
        private String userId;
        private Long amount;
        private Double roi;
        private Double minCreditScore;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Double getRoi() {
            return roi;
        }

        public void setRoi(Double roi) {
            this.roi = roi;
        }

        public Double getMinCreditScore() {
            return minCreditScore;
        }

        public void setMinCreditScore(Double minCreditScore) {
            this.minCreditScore = minCreditScore;
        }
    }

    public enum LentStatus {
        PENDING, EXECUTED, COMPLETED
    }

    public enum BorrowStatus {
        PENDING, EXECUTED, COMPLETED
    }

    public class BorrowDetails {
        private String id;
        private String userId;
        private Long amount;
        private Double creditScore;
        private Double roi;
        private BorrowStatus status;
        private String lentId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Double getCreditScore() {
            return creditScore;
        }

        public void setCreditScore(Double creditScore) {
            this.creditScore = creditScore;
        }

        public Double getRoi() {
            return roi;
        }

        public void setRoi(Double roi) {
            this.roi = roi;
        }

        public BorrowStatus getStatus() {
            return status;
        }

        public void setStatus(BorrowStatus status) {
            this.status = status;
        }

        public String getLentId() {
            return lentId;
        }

        public void setLentId(String lentId) {
            this.lentId = lentId;
        }
    }

    public class LentDetails {
        private String id;
        private String userId;
        private Double creditScore;
        private Long amount;
        private Double roi;
        private LentStatus status;
        private List<BorrowDetails> borrowLists;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Double getCreditScore() {
            return creditScore;
        }

        public void setCreditScore(Double creditScore) {
            this.creditScore = creditScore;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Double getRoi() {
            return roi;
        }

        public void setRoi(Double roi) {
            this.roi = roi;
        }

        public LentStatus getStatus() {
            return status;
        }

        public void setStatus(LentStatus status) {
            this.status = status;
        }

        public List<BorrowDetails> getBorrowLists() {
            return borrowLists;
        }

        public void setBorrowLists(List<BorrowDetails> borrowLists) {
            this.borrowLists = borrowLists;
        }
    }

    // lent records
    public static class lentDetailsList {
        public List<LentDetails> lentDetailsList;
    }

    public static class AcceptLendRequest { //used for settleBorrowRequest too

        private String lentId;
        private String borrowId;

        public AcceptLendRequest(String lentId, String borrowId) {
            this.lentId = lentId;
            this.borrowId = borrowId;
        }
    }

    public static class BorrowRequests {
        public List<BorrowDetails> borrowDetailsList;
    }




}