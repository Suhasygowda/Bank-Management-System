package in.mirai.bms.model;

import java.sql.Timestamp;

/**
 * Represents a single financial transaction in the system.
 * Tracks the type of transaction, the amount involved, and the time it occurred.
 */
public class Transaction {

    /** Unique identifier for the transaction (UUID) */
    private String transactionId;
    
    /** ID of the account associated with this transaction */
    private int accountId;
    
    /** Type of transaction (DEPOSIT, WITHDRAW, etc.) */
    private TransactionType type;
    
    /** Amount involved in the transaction */
    private int amount;
    
    /** Timestamp when the transaction was recorded */
    private Timestamp transactionTime;

    /** Default constructor */
    public Transaction() {
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", accountId=" + accountId +
                ", type=" + type +
                ", amount=" + amount +
                ", transactionTime=" + transactionTime +
                '}';
    }

    /**
     * Parameterized constructor for creating a new Transaction instance.
     *
     * @param transactionId Unique transaction ID.
     * @param accountId     Associated account ID.
     * @param type          Type of transaction.
     * @param amount        Transaction amount.
     */
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

    // Getters and Setters

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
