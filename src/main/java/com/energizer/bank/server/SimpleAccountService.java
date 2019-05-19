package com.energizer.bank.server;

import java.util.Objects;

class SimpleAccountService implements AccountService {

    @Override
    public void withdraw(int dollars, Account account) throws NotEnoughMoneyException, NotValidMoneyInputException {

        if (Objects.isNull(account)) {
            throw new IllegalArgumentException();
        }

        if (dollars <= 0) {
            throw new NotValidMoneyInputException();
        }

        if (account instanceof DepositAccount) {
            if (account.getDollars() < dollars) {
                throw new NotEnoughMoneyException();
            }
            account.setDollars(account.getDollars() - dollars);
        } else if (account instanceof CreditAccount) {
            CreditAccount creditAccount = (CreditAccount) account;
            if (account.getDollars() + creditAccount.getCreditDollars() < dollars) {
                throw new NotEnoughMoneyException();
            } else if (account.getDollars() >= dollars) {
                account.setDollars(account.getDollars() - dollars);
            } else {
                int creditVariable = (creditAccount.getCreditDollars() + account.getDollars() - dollars);
                account.setDollars(0);
                creditAccount.setCreditDollars(creditVariable);
            }
        }
    }

    @Override
    public void deposit(int dollars, Account account) throws NotValidMoneyInputException {
        if (Objects.isNull(account)) {
            throw new IllegalArgumentException();
        }
        if (dollars <= 0) {
            throw new NotValidMoneyInputException();
        }

        if (account instanceof DepositAccount) {
            account.setDollars(account.getDollars() + dollars);
        } else if (account instanceof CreditAccount) {
            CreditAccount creditAccount = (CreditAccount) account;
            if (CREDIT_ACCOUNT_LIMIT != creditAccount.getCreditDollars()) {

                int dolg = (CREDIT_ACCOUNT_LIMIT - creditAccount.getCreditDollars());
                if (dolg < dollars) {
                    creditAccount.setCreditDollars(creditAccount.getCreditDollars() + dolg);
                    account.setDollars(account.getDollars() + dollars - dolg);
                } else {
                    creditAccount.setCreditDollars(creditAccount.getCreditDollars() + dollars);
                }
            } else {
                account.setDollars(account.getDollars() + dollars);
            }
        }
    }

    @Override
    public void transfer(int dollars, Account from, Account to) throws NotEnoughMoneyException, NotValidMoneyInputException {
        if (Objects.isNull(from)) {
            throw new IllegalArgumentException();
        }
        if (Objects.isNull(to)) {
            throw new IllegalArgumentException();
        }
        withdraw(dollars, from);
        deposit(dollars, to);
    }


}
