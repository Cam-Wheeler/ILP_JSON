package dev.cameron.logistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// A basic DTO for endpoint 1 (a simple logger for payment).
public class PaymentLogDTO {

    @JsonProperty("type")
    private String method;
    @JsonProperty("account_number")
    private String accountNumber;
    private float amount;
    private String currency;
    private String date;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SimplePaymentLog{" +
                "method='" + method + '\'' +
                ", accountNumber=" + accountNumber +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
