package in.mirai.bms.dao;

import in.mirai.bms.model.Account;

public interface AccountDAO {

    boolean createAccount(Account account);

    Account getAccountById(int accountId, int pin);

    boolean updateBalance(int accountId, int amount, int pin);

    boolean transfer(int senderId,
                     int receiverId,
                     int amount,
                     int pin);
}