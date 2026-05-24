package in.mirai.bms.model;

import java.sql.Timestamp;

public class Account {

    private int accountId;
    private String holderName;
    private int balance;
    private int pin;
    private Timestamp createdAt;

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                ", pin=" + pin +
                ", createdAt=" + createdAt +
                '}';
    }

    public Account(int accountId, String holderName, int balance, int pin) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}