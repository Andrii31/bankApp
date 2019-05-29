package com.energizer.bank.server.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("DA")
public class DepositAccount extends Account implements Serializable {

}
