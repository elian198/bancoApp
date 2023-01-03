package com.bancoApp.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@SQLDelete(sql = "UPDATE users SET soft_delete=true WHERE id = ?")
@Where(clause = "soft_delete = false")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;

    private String password;

    @Column(name = "EMAIL" , unique = true)
    private String email;

    @Column(name = "PHONE", unique = true , length = 12)
    private Integer phone;

    @Column(name = "CUIT", unique = true, length = 11)
    private String cuit;

    @Column( name = "SOFT_DELETE")
    private Boolean soft_delete;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL},mappedBy="user")
    private  Set<Account> accounts =new HashSet();

    @OneToMany(cascade = {CascadeType.ALL},mappedBy="userDollar")
    private  Set<AccountDollar> accountsDollar =new HashSet();

    @OneToMany(cascade = {CascadeType.ALL},mappedBy="user")
    private  Set<FixedTerm> fixedTerms =new HashSet();
    public User() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getSoft_delete() {
        return soft_delete;
    }

    public void setSoft_delete(Boolean soft_delete) {
        this.soft_delete = soft_delete;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Set<AccountDollar> getAccountsDollar() {
        return accountsDollar;
    }

    public Set<FixedTerm> getFixedTerms() {
        return fixedTerms;
    }

    public void setFixedTerms(Set<FixedTerm> fixedTerms) {
        this.fixedTerms = fixedTerms;
    }

    public void setAccountsDollar(Set<AccountDollar> accountsDollar) {
        this.accountsDollar = accountsDollar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", cuit='" + cuit + '\'' +
                ", soft_delete=" + soft_delete +
                ", roles=" + roles +
                ", accounts=" + accounts +
                ", accountsDollar=" + accountsDollar +
                '}';
    }
}
