package in.mirai.bms.model;

/**
 * Defines the types of transactions supported by the Bank Management System.
 */
public enum TransactionType {
    /** Adding funds to an account */
    DEPOSIT,
    
    /** Removing funds from an account */
    WITHDRAW,
    
    /** Incoming funds from another account */
    TRANSFER_IN,
    
    /** Outgoing funds to another account */
    TRANSFER_OUT
}
