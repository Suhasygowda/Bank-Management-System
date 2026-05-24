package in.mirai.bms.serviceimpl;

import in.mirai.bms.dao.AccountDAO;
import in.mirai.bms.dao.TransactionDAO;
import in.mirai.bms.daoimpl.AccountDAOImpl;
import in.mirai.bms.daoimpl.TransactionDAOImpl;
import in.mirai.bms.model.Account;
import in.mirai.bms.model.Transaction;
import in.mirai.bms.model.TransactionType;
import in.mirai.bms.service.AccountService;
import in.mirai.bms.util.ConsoleUI;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {

    final AccountDAO accountDAO       = new AccountDAOImpl();
    final TransactionDAO transactionDAO = new TransactionDAOImpl();

    @Override
    public void createAccount(Account account) {
        boolean created = accountDAO.createAccount(account);
        if (created) {
            ConsoleUI.printSuccess("Account #" + account.getAccountId()
                    + " created for " + account.getHolderName() + ".");
            ConsoleUI.printTimestamp();
        } else {
            ConsoleUI.printError("Could not create account. Please try again later.");
        }
    }

    @Override
    public void viewAccount(int accountId, int pin) {
        accountDAO.getAccountById(accountId, pin).ifPresentOrElse(
                account -> ConsoleUI.printAccountCard(
                        account.getAccountId(),
                        account.getHolderName(),
                        account.getBalance()),
                () -> ConsoleUI.printError("Invalid Account ID or PIN.")
        );
    }

    @Override
    public void deposit(int accountId, int amount, int pin) {
        Account account = accountDAO.getAccountById(accountId, pin).orElse(null);

        if (account == null) {
            ConsoleUI.printError("Invalid Account ID or PIN.");
            return;
        }

        long before     = account.getBalance();
        long newBalance = before + amount;
        boolean updated = accountDAO.updateBalance(accountId, (int) newBalance, pin);

        if (updated) {
            recordTransaction(accountId, TransactionType.DEPOSIT, amount);
            ConsoleUI.printSuccess("Deposit successful.");
            ConsoleUI.printBalanceChange(before, newBalance);
            ConsoleUI.printTimestamp();
        }
    }

    @Override
    public void withdraw(int accountId, int amount, int pin) {
        Account account = accountDAO.getAccountById(accountId, pin).orElse(null);

        if (account == null) {
            ConsoleUI.printError("Invalid Account ID or PIN.");
            return;
        }

        if (account.getBalance() < amount) {
            ConsoleUI.printError("Insufficient balance. Available: ₹"
                    + String.format("%,d", account.getBalance()));
            return;
        }

        long before     = account.getBalance();
        long newBalance = before - amount;
        boolean updated = accountDAO.updateBalance(accountId, (int) newBalance, pin);

        if (updated) {
            recordTransaction(accountId, TransactionType.WITHDRAW, amount);
            ConsoleUI.printSuccess("Withdrawal successful.");
            ConsoleUI.printBalanceChange(before, newBalance);
            ConsoleUI.printTimestamp();
        }
    }

    @Override
    public void transfer(int senderId, int receiverId, int amount, int pin) {
        if (senderId == receiverId) {
            ConsoleUI.printError("Cannot transfer to the same account.");
            return;
        }

        Account sender = accountDAO.getAccountById(senderId, pin).orElse(null);
        if (sender == null) {
            ConsoleUI.printError("Invalid Sender Account or PIN.");
            return;
        }

        if (sender.getBalance() < amount) {
            ConsoleUI.printError("Insufficient balance. Available: ₹"
                    + String.format("%,d", sender.getBalance()));
            return;
        }

        boolean transferred = accountDAO.transfer(senderId, receiverId, amount, pin);

        if (transferred) {
            recordTransaction(senderId,   TransactionType.TRANSFER_OUT, amount);
            recordTransaction(receiverId, TransactionType.TRANSFER_IN,  amount);

            ConsoleUI.printSuccess("Transfer of ₹" + String.format("%,d", amount)
                    + " from #" + senderId + " → #" + receiverId + " completed.");
            ConsoleUI.printBalanceChange(sender.getBalance(),
                    sender.getBalance() - amount);
            ConsoleUI.printTimestamp();
        } else {
            ConsoleUI.printError("Transfer failed. Check balance or receiver details.");
        }
    }

    @Override
    public void transactionHistory(int accountId, int pin) {
        boolean valid = accountDAO.getAccountById(accountId, pin).isPresent();

        if (!valid) {
            ConsoleUI.printError("Invalid Account ID or PIN.");
            return;
        }

        List<Transaction> transactions = transactionDAO.getTransactionHistory(accountId);

        if (transactions.isEmpty()) {
            ConsoleUI.printInfo("No transactions found for account #" + accountId + ".");
            return;
        }

        ConsoleUI.printTransactionTableHeader();

        for (Transaction t : transactions) {
            boolean isCredit = t.getType() == TransactionType.DEPOSIT
                    || t.getType() == TransactionType.TRANSFER_IN;
            ConsoleUI.printTransactionRow(
                    t.getTransactionId(),
                    t.getType().name(),
                    isCredit,
                    t.getAmount(),
                    t.getTransactionTime().toString()
            );
        }

        ConsoleUI.printTransactionTableFooter(transactions.size());
    }

    // ── private helper ──────────────────────────────────────────────────────

    private void recordTransaction(int accountId, TransactionType type, int amount) {
        Transaction tx = new Transaction();
        tx.setTransactionId(UUID.randomUUID().toString());
        tx.setAccountId(accountId);
        tx.setType(type);
        tx.setAmount(amount);
        tx.setTransactionTime(new Timestamp(System.currentTimeMillis()));
        transactionDAO.saveTransaction(tx);
    }
}