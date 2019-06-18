package com.energizer.bank.server;

import com.energizer.bank.server.entity.Account;
import com.energizer.bank.server.entity.CreditAccount;
import com.energizer.bank.server.entity.DepositAccount;
import com.energizer.bank.server.exceptions.NotEnoughMoneyException;
import com.energizer.bank.server.exceptions.NotValidMoneyInputException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleAccountServiceTest {

    SimpleAccountService simpleAccountService = new SimpleAccountService();

    private Account createDepositAccount() {
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setDollars(200);
        return depositAccount;


    }


    private Account createCreditAccount() {
        CreditAccount creditAccount = new CreditAccount();
        creditAccount.setDollars(200);
        return creditAccount;


    }


    @Test(expected = NotValidMoneyInputException.class)
    public void withdrawShouldThrowExceptionWhenDollarsIsNegative() throws NotValidMoneyInputException, NotEnoughMoneyException {

        simpleAccountService.withdraw(-5, createDepositAccount());

    }


    @Test(expected = IllegalArgumentException.class)
    public void withdrawShouldThrowExceptionWhenAccountIsNull() throws NotValidMoneyInputException, NotEnoughMoneyException {

        simpleAccountService.withdraw(-5, null);

    }

    @Test
    public void withdrawShouldDecreaseDollars() throws NotValidMoneyInputException, NotEnoughMoneyException {

        Account depositAccount = createDepositAccount();
        simpleAccountService.withdraw(20, depositAccount);
        assertEquals(180, depositAccount.getDollars());

    }

    @Test(expected = NotEnoughMoneyException.class)
    public void withdrawShouldThrowExceptionWhenDollarsIsToMany() throws NotValidMoneyInputException, NotEnoughMoneyException {

        Account depositAccount = createDepositAccount();
        simpleAccountService.withdraw(1500, depositAccount);


    }


    //****************************************************************************************
    @Test(expected = NotValidMoneyInputException.class)
    public void depositShouldThrowExceptionWhenDollarsIsNegative() throws NotValidMoneyInputException {
        simpleAccountService.deposit(-100, createDepositAccount());

    }

    @Test(expected = IllegalArgumentException.class)
    public void depositShouldThrowExceptionWhenAccountIsNull() throws NotValidMoneyInputException {

        simpleAccountService.deposit(-5, null);

    }

    @Test(expected = NotEnoughMoneyException.class)
    public void depositShouldThrowExceptionWhenDollarsIsToMany() throws NotValidMoneyInputException, NotEnoughMoneyException {

        Account creditAccount = createCreditAccount();
        simpleAccountService.withdraw(1500, creditAccount);


    }

    @Test
    public void depositShouldDecreaseDollars() throws NotValidMoneyInputException, NotEnoughMoneyException {

        CreditAccount creditAccount1 = (CreditAccount) createCreditAccount();
        CreditAccount creditAccount = creditAccount1;
        simpleAccountService.withdraw(800, creditAccount);
        assertEquals(400, creditAccount.getCreditDollars());
        assertEquals(0, creditAccount.getDollars());
        simpleAccountService.deposit(100, creditAccount);
        assertEquals(500, creditAccount.getCreditDollars());
        simpleAccountService.deposit(1000, creditAccount);
        assertEquals(AccountService.CREDIT_ACCOUNT_LIMIT, creditAccount.getCreditDollars());
        assertEquals(500, creditAccount.getDollars());

    }


    @Test
    public void transfer() throws NotValidMoneyInputException, NotEnoughMoneyException {

        CreditAccount creditAccount1 = (CreditAccount) createCreditAccount();
        CreditAccount creditAccount = creditAccount1;
        Account depositAccount = createDepositAccount();

        creditAccount.setDollars(200);
        depositAccount.setDollars(500);


        simpleAccountService.transfer(600, creditAccount, depositAccount);
        assertEquals(600, creditAccount.getCreditDollars());
        assertEquals(0, creditAccount.getDollars());
        assertEquals(1100, depositAccount.getDollars());


    }
}