package com.bancoApp.service;

import com.bancoApp.entities.Account;
import com.bancoApp.entities.Card;

public interface AccountService {

    Account findById(Long id);
    Boolean addCard(Long idAccount, Card card);
    void transfer(String name, Long idSerder, double saldo);
}
