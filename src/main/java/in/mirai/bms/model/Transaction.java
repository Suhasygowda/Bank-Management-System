package in.mirai.bms.model;

import java.sql.Timestamp;

public class Transaction {

    private String transactionId;
    private int accountId;
    private TransactionType type;
    private int amount;
    private Timestamp transactionTime;

    public Transaction() {
    }

    public Transaction(String transactionId,
                       int accountId,
                       TransactionType type,
                       int amount) {

        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.transactionTime = new Timestamp(System.currentTimeMillis());
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }
}