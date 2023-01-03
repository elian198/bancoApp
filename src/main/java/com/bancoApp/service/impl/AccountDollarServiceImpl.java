package com.bancoApp.service.impl;

import com.bancoApp.entities.AccountDollar;
import com.bancoApp.repository.AccountDollarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDollarServiceImpl {

     @Autowired
     private AccountDollarRepository accountDollarRepository;

     public AccountDollarServiceImpl(AccountDollarRepository accountDollarRepository) {
          this.accountDollarRepository = accountDollarRepository;
     }

     public AccountDollar findById(Long id){
          return accountDollarRepository.findById(id).get();

     }
    public static Double total = 0.0;
     public Double buyByDay(Double dollar){
          total +=  dollar;
          if(dollar > 100){
               return total - dollar;
          }
          return total;
     }
}
