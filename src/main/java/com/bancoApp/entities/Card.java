package com.bancoApp.entities;

import com.bancoApp.entities.enums.CardType;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @Column(name = "Date_Time")
    private LocalDate localDate;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "CARD_PAYMENT",
            joinColumns = {
                    @JoinColumn(name = "CARD_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "PAYMENT_ID") })
    private Set<Payment> payments = new HashSet<>();
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardType=" + cardType +
                ", cardNumber='" + cardNumber + '\'' +
                ", localDate=" + localDate +
                ", payments=" + payments +
                '}';
    }
}
