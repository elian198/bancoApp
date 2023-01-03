package com.bancoApp.service.impl;

import com.bancoApp.dto.CardDto;
import com.bancoApp.dto.ConvertDto;
import com.bancoApp.entities.Account;
import com.bancoApp.entities.Card;
import com.bancoApp.entities.Payment;
import com.bancoApp.entities.enums.CardType;
import com.bancoApp.repository.CardRepository;
import com.bancoApp.service.CardService;
import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;

import javax.persistence.Convert;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Boolean existCardNumber(String number){
        if(cardRepository.findByCardNumber(number) != null){
            return true;
        }

        return false;
    }

    @Override
    public Card findById(Long id) {
        if(!cardRepository.existsById(id)){
            return null;
        }
        return cardRepository.findById(id).get();
    }

    @Override
    public Card findByTypeCard(Integer typeCard, Long id) {
      return   (cardRepository.findByTypeCard(typeCard, id) == null
       ? null : cardRepository.findByTypeCard(typeCard, id));

    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> orderByExpirationDate() {
        return cardRepository.orderByExpiration();
    }

    private Double interest(Double total, Integer count){

        if(count<13) {
            if (count > 3 && count < 6) {
                return total * 1.20;
            } else if (count > 5 && count < 9) {
                return total * 1.50;
            } else {
                return total * 2.0;
            }
        }
         return 0.0;
    }

    public Card findByCardNumber(String cardNumber){
        return cardRepository.findByCardNumber(cardNumber);

    }
    public Card addPayment(Long id, Payment payment){
          Card card = cardRepository.findById(id).get();
          Double total = interest(payment.getTotal(), payment.getAmounts()) / 6;
          payment.setInstallment(total);
          payment.setLocalDateTime(LocalDateTime.now());
          payment.setCard(card);
           card.getPayments().add(payment);
           cardRepository.save(card);
           return card;
    }

    public Double expenses(Long id){
        Double total = 0.0;
       Card card = cardRepository.findById(id).get();
        for(Payment list : card.getPayments()){
            total += list.getTotal();
        }
        return total;
    }


}
