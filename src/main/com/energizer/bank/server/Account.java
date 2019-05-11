package com.energizer.bank.server;

public abstract class Account {
    protected int dollars;

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public int getDollars() {
        return dollars;
    }
}
