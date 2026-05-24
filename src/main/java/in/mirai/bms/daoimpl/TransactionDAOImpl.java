package in.mirai.bms.daoimpl;

import in.mirai.bms.dao.TransactionDAO;
import in.mirai.bms.model.Transaction;

import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    @Override
    public boolean saveTransaction(Transaction transaction) {
        return false;
    }

    @Override
    public List<Transaction> getTransactionHistory(int accountId, int pin) {
        return List.of();
    }

}
