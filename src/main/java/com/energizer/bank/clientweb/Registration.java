package com.energizer.bank.clientweb;

import com.energizer.bank.server.Gender;
import com.energizer.bank.server.entity.Account;

import java.util.ArrayList;
import java.util.List;

public class Registration {

    private final String tmp = "qwerty";            // don't forget delete =)

    public String getTmp() {
        return tmp;
    }           // don't forget delete =)

    private String name;
    private String surname;
    private String email;


    private String accountType;

    private long password;
    private int age;
    private List<Account> accounts = new ArrayList<>();
    private String genderType;


    private Gender gender;


    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
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

    public void setAccounts(Account account) {
        this.accounts.add(account);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
