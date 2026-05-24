package in.mirai.bms.dao;

import in.mirai.bms.model.Transaction;

import java.util.List;

public interface TransactionDAO {

    boolean saveTransaction(Transaction transaction);

    List<Transaction> getTransactionHistory(
            int accountId, int pin
    );
}