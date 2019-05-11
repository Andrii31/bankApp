package com.energizer.bank.server;

 interface AccountService {

    int CREDIT_ACCOUNT_LIMIT = 1000;

    void withdraw(int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException;

    void deposit(int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException;

    void transfer(int dollars, Account from, Account to) throws NotEnoughMoneyException, NotValidMoneyInputException;


}
