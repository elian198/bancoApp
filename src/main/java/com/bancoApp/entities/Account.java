package com.bancoApp.entities;

import com.bancoApp.entities.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "SALDO")
    private Double saldo;

    @Column(name = "ACCOUNT_TYPE")
    private AccountType accountType;

    @JsonIgnore
    @ManyToOne
    private User user;

    public Account() { }


    public Account(AccountType accountType, User user) {

        this.accountType = accountType;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", alias='" + alias + '\'' +
                ", saldo=" + saldo +
                ", accountType=" + accountType +
                ", user=" + user +
                '}';
    }
}
