package com.energizer.bank.server;

import java.util.List;

public class Client {
    private String name, surmame, email;
    private int age;
    private List<Account> accounts;
    private Gender gender;

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
}
