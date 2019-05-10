package com.energizer.bank.server;

 interface AccountService {

    public void withdraw(int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException;

    public void deposit (int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException;

    public void transfer (int dollars, Account from, Account to) throws NotEnoughMoneyException, NotValidMoneyInputException;


}
