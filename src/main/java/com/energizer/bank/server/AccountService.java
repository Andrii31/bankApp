package com.energizer.bank.server;

import com.energizer.bank.server.entity.Account;

import java.util.List;
import java.util.Map;

public interface AccountService {

    int CREDIT_ACCOUNT_LIMIT = 1000;
    //via trans

    void withdraw(int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException;

    void deposit(int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException;

    void transfer(int dollars, Account from, Account to) throws NotEnoughMoneyException, NotValidMoneyInputException;

    /**
     * Returns id of account and available money. If there is no accounts or no money on accounts, returns empty map
     */
    Map<Long, Integer> getAvailableMoney(List<Account> accounts);

    Map<Long, Integer> getDebts(List<Account> accounts);


}
