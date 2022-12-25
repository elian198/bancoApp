package com.bancoApp.service.impl;

import com.bancoApp.entities.Account;
import com.bancoApp.entities.Card;
import com.bancoApp.entities.Transfer;
import com.bancoApp.entities.User;
import com.bancoApp.entities.enums.AccountType;
import com.bancoApp.repository.AccountRepository;
import com.bancoApp.repository.CardRepository;
import com.bancoApp.repository.TransferRepository;
import com.bancoApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CardRepository cardRepository;


    public AccountServiceImpl(AccountRepository accountRepository, TransferRepository transferRepository, UserServiceImpl userService, CardRepository cardRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
        this.userService = userService;
        this.cardRepository = cardRepository;
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public void addCard(Long idAccount, Card card) {
        if(accountRepository.existsById(idAccount)){
            Account account = accountRepository.findById(idAccount).get();
            account.getCards().add(card);
            accountRepository.save(account);
        }

    }

    @Override
    public void transfer(String name, Long idSender, double saldo) {
        if(accountRepository.findById(idSender) ==  null){
            System.out.println("EL ID DEL REMITENTE NO EXISTE");
        }
         User user = userService.findByUserName(name);

          for (Account list : user.getAccounts()) {
              if (list.getAccountType().equals(AccountType.PESOS)) {
                  Account account = accountRepository.findById(list.getId()).get();
                  Double oldSaldo = account.getSaldo();
                  Account accountSender = accountRepository.findById(idSender).get();
                  if (account.getSaldo() > saldo && saldo < 10000.0) {
                      double totalSender = accountSender.getSaldo() + saldo;
                      accountSender.setSaldo(totalSender);
                      accountRepository.save(accountSender);
                      double total = account.getSaldo() - saldo;
                      account.setSaldo(total);
                      accountRepository.save(account);
                      Account accountTrans = accountRepository.findById(list.getId()).get();

                     Transfer transfer = new Transfer(list.getId(), accountSender.getId(), saldo,"TRASFERENCIA"
                              , LocalDateTime.now(),account.getAlias(), accountSender.getAlias(),
                             oldSaldo, accountTrans.getSaldo());
                      transferRepository.save(transfer);
                  }
              }
          }
    }
}

