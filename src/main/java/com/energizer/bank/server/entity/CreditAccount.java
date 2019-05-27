package com.energizer.bank.server.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

import static com.energizer.bank.server.AccountService.CREDIT_ACCOUNT_LIMIT;

@Entity
@DiscriminatorValue("CA")
public class CreditAccount extends Account implements Serializable {

    @Column(name = "credit_dollars")
    private int creditDollars = CREDIT_ACCOUNT_LIMIT;

    public int getCreditDollars() {
        return creditDollars;
    }

    public void setCreditDollars(int creditDollars) {
        this.creditDollars = creditDollars;
    }


}
