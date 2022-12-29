package com.bancoApp.entities;

import com.bancoApp.entities.enums.CardType;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CARDS")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CARD_TYPE")
    private CardType cardType;

    @Column(name = "CARD_NUMBER", unique = true, length = 20)
    private String cardNumber;

    @Column(name = "Date_Time")
    private LocalDateTime localDate;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Column(name = "LIMIT")
    private Double limit = 200000.0;

    @OneToMany(cascade = {CascadeType.ALL},mappedBy="card")
    private  Set<Payment> payments =new HashSet();
    public Card() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setLocalDate(LocalDateTime localDate) {
        this.localDate = localDate;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardType=" + cardType +
                ", cardNumber='" + cardNumber + '\'' +
                ", localDate=" + localDate +
                ", expirationDate=" + expirationDate +
                ", limit=" + limit +
                ", payments=" + payments +
                '}';
    }
}
