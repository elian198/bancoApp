package com.bancoApp.service;

import com.bancoApp.dto.CardDto;
import com.bancoApp.entities.Card;
import com.bancoApp.entities.enums.CardType;

import java.util.List;
import java.util.Set;

public interface CardService {

    Card findById(Long id);
    Card findByTypeCard(Integer typeCard, Long id);
    List<Card> findAll();
    List<Card> orderByExpirationDate();
}
