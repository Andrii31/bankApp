package com.energizer.bank.server.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account")
@DiscriminatorColumn(name = "ACC_TYPE")
public abstract class Account implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "account_dollars")
    protected int dollars;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    public Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public int getDollars() {
        return dollars;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account() {
    }

}
