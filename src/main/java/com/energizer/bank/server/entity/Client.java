package com.energizer.bank.server.entity;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.ListIndexBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Client implements Cloneable, Serializable {                  //Сlonable для тестирования
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String name;
    @Column(name = "first_surname")
    private String surmame;
    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListIndexBase(1)
    private List<Account> accounts = new ArrayList<>();

//    @Transient
    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)       //EnumType.STRING - в БД - имя из enum. Сейчас - порядковый номер из enum
    private Gender gender;

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurmame() {
        return surmame;
    }

    public void setSurmame(String surmame) {
        this.surmame = surmame;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Object clone() throws CloneNotSupportedException {                            //для тестирования
        return super.clone();
    }


}
