package com.bancoApp.repository;

import com.bancoApp.dto.CardDto;
import com.bancoApp.entities.Card;
import com.bancoApp.entities.User;
import com.bancoApp.entities.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value = "SELECT * FROM cards WHERE cards.card_number =  :filtro" ,nativeQuery = true)
    Card findByCardNumber(@Param("filtro") String number);

    @Query(value = "SELECT * FROM cards order by cards.expiration_date" ,nativeQuery = true)
    List<Card> orderByExpiration();

    @Query(value = "SELECT * FROM cards WHERE cards.card_type  =  :filtro AND cards.id = :id" ,nativeQuery = true)
    Card findByTypeCard(@Param("filtro")Integer cardType, @Param("id")Long id);

}
