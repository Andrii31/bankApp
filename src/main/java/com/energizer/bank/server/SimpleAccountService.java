package com.energizer.bank.server;


import com.energizer.bank.server.entity.Account;
import com.energizer.bank.server.entity.CreditAccount;
import com.energizer.bank.server.entity.DepositAccount;
import com.energizer.bank.server.exceptions.NotEnoughMoneyException;
import com.energizer.bank.server.exceptions.NotValidMoneyInputException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SimpleAccountService implements AccountService {


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
        //Счетчики ненулевого балланса. Если >0, то деньги есть на аккаунте
        int notNullOnDepositAccCounter = 0;
        int notNullOnCreditAccCounter = 0;
        if (!accounts.isEmpty()) {
            for (Account acc : accounts) {
                //Если акк депозитный
                if (acc instanceof DepositAccount) {
                    //Проверка на ненулевой балланс
                    if (acc.getDollars() > 0) {
                        notNullOnDepositAccCounter++;
                        tmpMapAvailableMoneyForReturn.put(acc.getId(), acc.getDollars());
                    } else
                        notNullOnDepositAccCounter--;
                }
                //Если акк кредитный
                if (acc instanceof CreditAccount) {
                    CreditAccount acc1 = (CreditAccount) acc;
                    //Проверка на ненулевой балланс
                    if ((acc1.getDollars() + acc1.getCreditDollars()) > 0) {
                        notNullOnCreditAccCounter++;
                        //сумма денег кредит+доллары
                        tmpMapAvailableMoneyForReturn.put(acc.getId(), acc1.getDollars() + acc1.getCreditDollars());
                    } else
                        notNullOnCreditAccCounter--;
                }
            }
        } else
            //если аккаунтов нет - то возвращаем пустую мапу
            return null;
        //если денег нет - то возвращаем пустую мапу
        if (notNullOnDepositAccCounter <= 0 & notNullOnCreditAccCounter <= 0)
            return null;
        else
            //если деньги есть - то сформированную временную мапу
            return tmpMapAvailableMoneyForReturn;
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
