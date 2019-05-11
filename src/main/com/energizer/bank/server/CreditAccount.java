package com.energizer.bank.server;

public class CreditAccount extends Account {
    protected int tmpCredit;
    protected int creditDollars = tmpCredit;

    public CreditAccount() {
        this.tmpCredit = 1000;
    }

    public int getTmpCredit() {
        return tmpCredit;
    }

    public int getCreditDollars() {
        return creditDollars;
    }

    public void setCreditDollars(int creditDollars) {
        this.creditDollars = creditDollars;
    }
}
