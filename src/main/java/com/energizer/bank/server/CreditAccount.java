package com.energizer.bank.server;

import static com.energizer.bank.server.AccountService.CREDIT_ACCOUNT_LIMIT;

public class CreditAccount extends Account {

    protected int creditDollars = CREDIT_ACCOUNT_LIMIT;

    public int getCreditDollars() {
        return creditDollars;
    }

    public void setCreditDollars(int creditDollars) {
        this.creditDollars = creditDollars;
    }
}
