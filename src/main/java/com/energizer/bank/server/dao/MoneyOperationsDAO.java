package com.energizer.bank.server.dao;

import com.energizer.bank.server.entity.Account;

public interface MoneyOperationsDAO {

    void withdraw(int dollars, Account account);

    void deposit (int dollars, Account account);

    void transfer (int dollars, Account from, Account to);
}
