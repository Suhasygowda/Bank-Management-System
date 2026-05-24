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

    final AccountDAO accountDAO = new AccountDAOImpl();
    final TransactionDAO transactionDAO = new TransactionDAOImpl();

    @Override
    public void createAccount(Account account) {
        boolean created = accountDAO.createAccount(account);
        if (created) {
            ConsoleUI.printSuccess("Account Created Successfully!");
        } else {
            ConsoleUI.printError("Could Not Create account. Please try again later!");
        }
    }

    @Override
    public void viewAccount(int accountId, int pin) {
        accountDAO.getAccountById(accountId, pin).ifPresentOrElse(
            account -> {
                System.out.println("\n" + ConsoleUI.CYAN + "------- Account Details -------" + ConsoleUI.RESET);
                System.out.println(ConsoleUI.BOLD + "Account ID   : " + ConsoleUI.RESET + account.getAccountId());
                System.out.println(ConsoleUI.BOLD + "Holder Name  : " + ConsoleUI.RESET + account.getHolderName());
                System.out.println(ConsoleUI.BOLD + "Balance      : " + ConsoleUI.RESET + ConsoleUI.GREEN + "₹" + account.getBalance() + ConsoleUI.RESET);
                System.out.println(ConsoleUI.CYAN + "-------------------------------" + ConsoleUI.RESET);
            },
            () -> ConsoleUI.printError("Invalid Account ID or PIN!")
        );
    }

    @Override
    public void deposit(int accountId, int amount, int pin) {
        Account account = accountDAO.getAccountById(accountId, pin).orElse(null);

        if (account == null) {
            ConsoleUI.printError("Invalid Account ID or PIN!");
            return;
        }

        int newBalance = account.getBalance() + amount;
        boolean updated = accountDAO.updateBalance(accountId, newBalance, pin);

        if (updated) {
            Transaction transaction = new Transaction();
            transaction.setTransactionId(UUID.randomUUID().toString());
            transaction.setAccountId(accountId);
            transaction.setType(TransactionType.DEPOSIT);
            transaction.setAmount(amount);
            transaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));

            transactionDAO.saveTransaction(transaction);
            ConsoleUI.printSuccess("Deposit Successful! New Balance: ₹" + newBalance);
        }
    }

    @Override
    public void withdraw(int accountId, int amount, int pin) {
        Account account = accountDAO.getAccountById(accountId, pin).orElse(null);

        if (account == null) {
            ConsoleUI.printError("Invalid Account ID or PIN!");
            return;
        }

        if (account.getBalance() < amount) {
            ConsoleUI.printError("Insufficient Balance!");
            return;
        }

        int newBalance = account.getBalance() - amount;
        boolean updated = accountDAO.updateBalance(accountId, newBalance, pin);

        if (updated) {
            Transaction transaction = new Transaction();
            transaction.setTransactionId(UUID.randomUUID().toString());
            transaction.setAccountId(accountId);
            transaction.setType(TransactionType.WITHDRAW);
            transaction.setAmount(amount);
            transaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));

            transactionDAO.saveTransaction(transaction);
            ConsoleUI.printSuccess("Withdrawal Successful! Remaining Balance: ₹" + newBalance);
        }
    }

    @Override
    public void transfer(int senderId, int receiverId, int amount, int pin) {
        if (senderId == receiverId) {
            ConsoleUI.printError("Cannot transfer to the same account!");
            return;
        }

        Account sender = accountDAO.getAccountById(senderId, pin).orElse(null);
        if (sender == null) {
            ConsoleUI.printError("Invalid Sender Account or PIN!");
            return;
        }

        boolean transferred = accountDAO.transfer(senderId, receiverId, amount, pin);

        if (transferred) {
            // Sender transaction
            Transaction out = new Transaction();
            out.setTransactionId(UUID.randomUUID().toString());
            out.setAccountId(senderId);
            out.setType(TransactionType.TRANSFER_OUT);
            out.setAmount(amount);
            out.setTransactionTime(new Timestamp(System.currentTimeMillis()));
            transactionDAO.saveTransaction(out);

            // Receiver transaction
            Transaction in = new Transaction();
            in.setTransactionId(UUID.randomUUID().toString());
            in.setAccountId(receiverId);
            in.setType(TransactionType.TRANSFER_IN);
            in.setAmount(amount);
            in.setTransactionTime(new Timestamp(System.currentTimeMillis()));
            transactionDAO.saveTransaction(in);

            ConsoleUI.printSuccess("Transfer Successful!");
        } else {
            ConsoleUI.printError("Transfer Failed! Check balance or receiver details.");
        }
    }

    @Override
    public void transactionHistory(int accountId, int pin) {
        boolean valid = accountDAO.getAccountById(accountId, pin).isPresent();

        if (!valid) {
            ConsoleUI.printError("Invalid Account ID or PIN!");
            return;
        }

        List<Transaction> transactions = transactionDAO.getTransactionHistory(accountId);

        if (transactions.isEmpty()) {
            ConsoleUI.printInfo("No transactions found for this account.");
            return;
        }

        System.out.println("\n" + ConsoleUI.WHITE + "--------------------------------------------------------------------------------");
        System.out.printf(ConsoleUI.BOLD + "%-38s | %-12s | %-8s | %-20s\n" + ConsoleUI.RESET, 
                          "Transaction ID", "Type", "Amount", "Timestamp");
        System.out.println("--------------------------------------------------------------------------------");

        for (Transaction t : transactions) {
            String color = t.getType() == TransactionType.DEPOSIT || t.getType() == TransactionType.TRANSFER_IN 
                           ? ConsoleUI.GREEN : ConsoleUI.RED;
            
            System.out.printf("%-38s | %s%-12s%s | %s₹%-7d%s | %-20s\n",
                    t.getTransactionId(),
                    color, t.getType(), ConsoleUI.RESET,
                    color, t.getAmount(), ConsoleUI.RESET,
                    t.getTransactionTime());
        }
        System.out.println("--------------------------------------------------------------------------------" + ConsoleUI.RESET);
    }
}
