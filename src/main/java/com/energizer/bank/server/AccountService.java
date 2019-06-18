package com.energizer.bank.server;

import com.energizer.bank.server.entity.Account;
import com.energizer.bank.server.exceptions.NotEnoughMoneyException;
import com.energizer.bank.server.exceptions.NotValidMoneyInputException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
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
