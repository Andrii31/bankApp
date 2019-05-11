package com.energizer.bank.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleAccountServiceTest {

    SimpleAccountService simpleAccountService = new SimpleAccountService();



    @Test (expected = NotValidMoneyInputException.class)
    public void withdrawShouldThrowExceptionWhenDollarsIsNegative() throws NotValidMoneyInputException, NotEnoughMoneyException {

        simpleAccountService.withdraw(-5, createDepositAccount());

    }


    @Test (expected = IllegalArgumentException.class)
    public void withdrawShouldThrowExceptionWhenAccountIsNull() throws NotValidMoneyInputException, NotEnoughMoneyException {

        simpleAccountService.withdraw(-5, null);

    }

    @Test
    public void withdrawShouldDecreaseDollars() throws NotValidMoneyInputException, NotEnoughMoneyException {

        Account depositAccount = createDepositAccount();
        simpleAccountService.withdraw(20, depositAccount);
        assertEquals(18, depositAccount.getDollars());

    }






    private Account createDepositAccount() {
        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setDollars(200);
        return depositAccount;


    }

    @Test
    public void deposit() {
    }
}