package in.mirai.bms.model;

import java.sql.Timestamp;

/**
 * Represents a bank account in the system.
 * Contains account identity, ownership information, balance, and security credentials.
 */
public class Account {

    /** Unique identifier for the account */
    private int accountId;
    
    /** Name of the account holder */
    private String holderName;
    
    /** Current balance of the account in INR */
    private int balance;
    
    /** Security PIN for accessing the account */
    private int pin;
    
    /** Timestamp when the account was created */
    private Timestamp createdAt;

    /** Default constructor */
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

    /**
     * Parameterized constructor for creating a new Account instance.
     *
     * @param accountId  The unique ID for the account.
     * @param holderName The name of the account holder.
     * @param balance    The initial balance.
     * @param pin        The 4-digit security PIN.
     */
    public Account(int accountId, String holderName, int balance, int pin) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters with basic descriptions

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
