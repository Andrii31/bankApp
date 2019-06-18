package com.energizer.bank.clientweb;

public class Autorization {

    private final String tmp = "qwerty";  //for tmp testing ---user already exist

    private String login;
    private long password;

    public String getTmp() {
        return tmp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }
}
