package com.enfotrix.principalportal.models;

public class Model_AmountDetail {
    String Date,Amount;

    public Model_AmountDetail(String date, String amount) {
        Date = date;
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
