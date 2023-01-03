package com.bancoApp.dto;

import com.bancoApp.entities.Card;

import java.util.HashSet;
import java.util.Set;

public class ConvertDto {

    public Set<CardDto> CardEntitiesToDto(Set<Card> card){
        Set<CardDto> cardDtos = new HashSet<>();
        for(Card list : card){
            cardDtos.add(new CardDto(list.getId(),list.getCardType(),list.getCardNumber()
                    ,list.getLocalDate(),list.getExpirationDate()));
        }
        return cardDtos;
    }
}
