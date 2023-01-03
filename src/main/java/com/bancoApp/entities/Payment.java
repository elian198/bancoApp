package com.bancoApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENTS")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Double total;

    @Column(name = "amounts")
    private Integer amounts;

    @Column(name = "installment")
    private Double installment;

    @Column(name = "LOCAL_DATE")
    private LocalDateTime localDateTime;

    @ManyToOne
    private Card card;
    public Payment() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Double getInstallment() {
        return installment;
    }

    public void setInstallment(Double installment) {
        this.installment = installment;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", total=" + total +
                ", amounts=" + amounts +
                ", installment=" + installment +
                ", localDateTime=" + localDateTime +
                '}';
    }
}


