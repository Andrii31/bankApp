package com.energizer.bank.server.entity;

import com.energizer.bank.server.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "naint")}
)
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String name;

    @Column(name = "surname")
    private String surmame;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "password")
    private long password;


    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Account> accounts = new ArrayList<>();

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

    public String getSurname() {
        return surmame;
    }

    public void setSurname(String surmame) {
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

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return email.equals(client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
