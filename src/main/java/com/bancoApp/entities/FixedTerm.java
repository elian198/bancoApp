package com.bancoApp.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FIXEDTERMS")
public class FixedTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOCAL_DATE")
    private LocalDate localDate = LocalDate.now();

    @Column(name = "amount")
    private Double amount;

    @Column(name = "NUMBER_DAY")
    private int numberDay;

    @Column(name = "STATE")
    private Boolean state = false;


    @ManyToOne()
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public int getNumberDay() {
        return numberDay;
    }

    public void setNumberDay(int numberDay) {
        this.numberDay = numberDay;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }



    @Override
    public String toString() {
        return "FixedTerm{" +
                "id=" + id +
                ", localDate=" + localDate +
                ", numberDay=" + numberDay +
                ", user=" + user +
                '}';
    }
}
