package in.mirai.bms.dao;

import in.mirai.bms.model.Account;
import java.util.Optional;

/**
 * Interface for Account Data Access Objects.
 * Defines the contract for database operations related to accounts.
 */
public interface AccountDAO {

    /**
     * Creates a new account in the database.
     *
     * @param account The account object containing details to be saved.
     * @return {@code true} if account creation was successful, {@code false} otherwise.
     */
    boolean createAccount(Account account);

    /**
     * Retrieves an account from the database by ID and PIN validation.
     *
     * @param accountId The unique ID of the account.
     * @param pin       The security PIN for validation.
     * @return An {@link Optional} containing the Account if found and PIN matches, otherwise empty.
     */
    Optional<Account> getAccountById(int accountId, int pin);

    /**
     * Updates the balance of an existing account.
     *
     * @param accountId The unique ID of the account.
     * @param amount    The new balance amount to set.
     * @param pin       The security PIN for validation.
     * @return {@code true} if update was successful, {@code false} otherwise.
     */
    boolean updateBalance(int accountId, int amount, int pin);

    /**
     * Performs a fund transfer between two accounts with atomicity.
     *
     * @param senderId   The ID of the account sending funds.
     * @param receiverId The ID of the account receiving funds.
     * @param amount     The amount to transfer.
     * @param pin        The security PIN of the sender.
     * @return {@code true} if transfer was successful, {@code false} otherwise.
     */
    boolean transfer(int senderId,
                     int receiverId,
                     int amount,
                     int pin);
}
