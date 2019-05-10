package com.energizer.bank.server;

public class CreditAccount extends Account {
    protected int tmpCredit;
    protected int creditDollars = tmpCredit;

    public CreditAccount() {
        this.tmpCredit = 1000;
    }
}
