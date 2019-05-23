package com.energizer.bank.server;

import jdk.nashorn.internal.objects.NativeArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * Returns id of account and available money. If there is no accounts or no money on accounts, returns empty map
     */
    @Override
    public Map<Long, Integer> getAvailableMoney(List<Account> accounts) {
        Map<Long, Integer> tmpMapAvailableMoneyForReturn = new HashMap<>();
        int notNullOnAccCounter = 0;                                                                                    //Счетчик ненулевого балланса. Если >0, то деньги есть на аккаунте
        if (!accounts.isEmpty()) {
            for (Account acc : accounts) {
                if (acc instanceof DepositAccount) {                                                                    //Если акк депозитный
                    if (acc.getDollars() != 0)
                        notNullOnAccCounter++;                                                                          //Проверка на ненулевой балланс
                    else notNullOnAccCounter--;
                    tmpMapAvailableMoneyForReturn.put(acc.getId(), acc.getDollars());
                } else if (acc instanceof CreditAccount) {                                                              //Если акк кредитный
                    CreditAccount acc1 = (CreditAccount) acc;
                    if ((acc1.getDollars() + acc1.getCreditDollars()) != 0)
                        notNullOnAccCounter++;                      //Проверка на ненулевой балланс
                    else notNullOnAccCounter--;
                    tmpMapAvailableMoneyForReturn.put(acc.getId(), acc1.getDollars() + acc1.getCreditDollars());        //сумма денег кредит+доллары
                }
            }
        } else
            return null;                                                                                                //если аккаунтов нет - то возвращаем пустую мапу
        if (notNullOnAccCounter <= 0)
            return null;                                                                                                //если денег нет - то возвращаем пустую мапу
        else
            return tmpMapAvailableMoneyForReturn;                                                                       //если деньги есть - то сформированную временную мапу
    }


    /**
     * Returns id of account and debt. If there is no credit accounts or no debts on accounts, returns empty map
     */
    @Override
    public Map<Long, Integer> getDebts(List<Account> accounts) {
        Map<Long, Integer> tmpMapDebitMoneyForReturn = new HashMap<>();
        if (!accounts.isEmpty()) {
            for (Account acc : accounts) {
                if (acc instanceof CreditAccount) {                                                                     //Если акк кредитный
                    CreditAccount acc1 = (CreditAccount) acc;
                    if (acc1.getCreditDollars() != CREDIT_ACCOUNT_LIMIT)                                                //А есть ли долг на аккаунте?
                        tmpMapDebitMoneyForReturn.put(acc1.getId(), acc1.getCreditDollars() - CREDIT_ACCOUNT_LIMIT);    //Если есть - то добавляем в мапу
                }
            }
        } else
            return null;                                                                                                //если аккаунтов нет - то возвращаем пустую мапу
        if (tmpMapDebitMoneyForReturn.isEmpty())
            return null;                                                                                                //если долга или нет кредитного аккаунта - то возвращаем пустую мапу
        else
            return tmpMapDebitMoneyForReturn;                                                                           //если долг есть есть - то возвращаем сформированную временную мапу
    }


}
