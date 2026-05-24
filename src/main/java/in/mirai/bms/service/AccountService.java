package in.mirai.bms.service;

import in.mirai.bms.model.Account;

/**
 * Service interface defining the business logic for Bank Account operations.
 * Acts as an intermediary between the UI and the Data Access Layer.
 */
public interface AccountService {

    /**
     * Orchestrates the creation of a new bank account.
     *
     * @param account The account details to be saved.
     */
    void createAccount(Account account);

    /**
     * Retrieves and displays account details to the user.
     *
     * @param accountId The ID of the account to view.
     * @param pin       The security PIN for authorization.
     */
    void viewAccount(int accountId, int pin);

    /**
     * Processes a deposit transaction.
     *
     * @param accountId The ID of the account.
     * @param amount    The amount to deposit.
     * @param pin       The security PIN for authorization.
     */
    void deposit(int accountId, int amount, int pin);

    /**
     * Processes a withdrawal transaction.
     *
     * @param accountId The ID of the account.
     * @param amount    The amount to withdraw.
     * @param pin       The security PIN for authorization.
     */
    void withdraw(int accountId,
                  int amount,
                  int pin);

    /**
     * Orchestrates a fund transfer between two accounts and logs the transactions.
     *
     * @param senderId   The sender's account ID.
     * @param receiverId The receiver's account ID.
     * @param amount     The amount to transfer.
     * @param pin        The sender's security PIN.
     */
    void transfer(int senderId,
                  int receiverId,
                  int amount,
                  int pin);

    /**
     * Fetches and displays the transaction history for an account.
     *
     * @param accountId The ID of the account.
     * @param pin       The security PIN for authorization.
     */
    void transactionHistory(int accountId, int pin);
}
