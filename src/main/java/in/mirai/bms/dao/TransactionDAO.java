package in.mirai.bms.dao;

import in.mirai.bms.model.Transaction;
import java.util.List;

/**
 * Interface for Transaction Data Access Objects.
 * Defines the contract for logging and retrieving transaction records.
 */
public interface TransactionDAO {

    /**
     * Saves a transaction record to the database.
     *
     * @param transaction The transaction object to be logged.
     * @return {@code true} if saving was successful, {@code false} otherwise.
     */
    boolean saveTransaction(Transaction transaction);

    /**
     * Retrieves the complete transaction history for a specific account.
     *
     * @param accountId The ID of the account whose history is requested.
     * @return A list of {@link Transaction} objects, ordered by time.
     */
    List<Transaction> getTransactionHistory(int accountId);
}
