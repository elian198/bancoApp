package com.bancoApp.dto;

import com.bancoApp.entities.Card;
import com.bancoApp.entities.enums.CardType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDto {

    private Long id;
    private CardType cardType;
    private String cardNumber;
    private LocalDateTime localDate;
    private LocalDate expirarionDate;



    public CardDto() { }

    public CardDto(Long id, CardType cardType, String cardNumber, LocalDateTime localDate, LocalDate expirarionDate) {
        this.id = id;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.localDate = localDate;
        this.expirarionDate = expirarionDate;
    }

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

    public void setLocalDate(LocalDateTime localDate) {
        this.localDate = localDate;
    }

    public LocalDate getExpirarionDate() {
        return expirarionDate;
    }

    public void setExpirarionDate(LocalDate expirarionDate) {
        this.expirarionDate = expirarionDate;
    }

    public Card convertToEntities(){
        Card card = new Card();
        card.setId(getId());
        card.setCardNumber(getCardNumber());
        card.setLocalDate(getLocalDate());
        card.setCardType(getCardType());
        card.setExpirationDate(getExpirarionDate());

        return card;
    }

    @Override
    public String toString() {
        return "CardDto{" +
                "id=" + id +
                ", cardType=" + cardType +
                ", cardNumber='" + cardNumber + '\'' +
                ", localDate=" + localDate +
                ", expirarionDate=" + expirarionDate +
                '}';
    }
}
