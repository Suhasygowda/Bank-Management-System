package in.mirai.bms.service;

import in.mirai.bms.model.Account;

public interface AccountService {

    void createAccount(Account account);

    void viewAccount(int accountId, int pin);

    void deposit(int accountId, int amount, int pin);

    void withdraw(int accountId,
                  int amount,
                  int pin);

    void transfer(int senderId,
                  int receiverId,
                  int amount,
                  int pin);

    void transactionHistory(int accountId, int pin);
}