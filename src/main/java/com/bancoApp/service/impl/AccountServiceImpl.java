package com.bancoApp.service.impl;

import com.bancoApp.entities.*;
import com.bancoApp.entities.enums.AccountType;
import com.bancoApp.entities.enums.CardType;
import com.bancoApp.repository.AccountDollarRepository;
import com.bancoApp.repository.AccountRepository;
import com.bancoApp.repository.FixedReository;
import com.bancoApp.repository.TransferRepository;
import com.bancoApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FixedReository fixedReository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AccountDollarRepository accountDollarRepository;
    @Autowired
    private CardServiceImpl cardService;

    @Autowired
    private RestTemplate restTemplate;


    public AccountServiceImpl(AccountRepository accountRepository, FixedReository fixedReository,
                              TransferRepository transferRepository, UserServiceImpl userService,
                              AccountDollarRepository accountDollarRepository,
                              CardServiceImpl cardService, RestTemplate restTemplate) {
        this.accountRepository = accountRepository;
        this.fixedReository = fixedReository;
        this.transferRepository = transferRepository;
        this.userService = userService;
        this.accountDollarRepository = accountDollarRepository;
        this.cardService = cardService;
        this.restTemplate = restTemplate;
    }

    @Override
    public Account findById(Long id) {

        return accountRepository.findById(id).get();
    }

     public Integer countCards(Long id) {
           Account account = findById(id);
           return account.getCards().size();
     }

     public Boolean existTypeCard(Long id, CardType cardType){
         Account account = findById(id);
         for(Card list : account.getCards()){
             if(list.getCardType().equals(cardType)){
                 return true;
             }
         }
         return false;
     }
    @Override
    public  Boolean addCard(Long idUser, Card card) {
        Account account = accountRepository.findById(idUser).get();
        if (userService.findById(idUser) != null && !existTypeCard(idUser, card.getCardType())) {
            if(cardService.findByCardNumber(card.getCardNumber()) == null) {
                        card.setLocalDate(LocalDateTime.now());
                        account.getCards().add(card);
                        accountRepository.save(account);
                        return true;
                    }
            }

        
        return false;

    }

    @Override
    public void transfer(String name, Long idSender, double saldo) {
        if(accountRepository.findById(idSender) ==  null){
            System.out.println("EL ID DEL REMITENTE NO EXISTE");
        }
         User user = userService.findByUserName(name);

                  Long accountpesos = accountRepository.findById(user.getId()).get().getId();
                  Account account = accountRepository.findById(accountpesos).get();
                  Double oldSaldo = account.getSaldo();
                  Account accountSender = accountRepository.findById(idSender).get();
                  if (account.getSaldo() > saldo && saldo < 10000.0) {
                      double totalSender = accountSender.getSaldo() + saldo;
                      accountSender.setSaldo(totalSender);
                      accountRepository.save(accountSender);
                      double total = account.getSaldo() - saldo;
                      account.setSaldo(total);
                      accountRepository.save(account);
                      Account accountTrans = accountRepository.findById(accountpesos).get();

                     Transfer transfer = new Transfer(accountpesos, accountSender.getId(), saldo,"transferencia"
                              , LocalDateTime.now(),account.getAlias(), accountSender.getAlias(),
                             oldSaldo, accountTrans.getSaldo());
                      transferRepository.save(transfer);
                  }
              }


    public Long findDollarAccount(Long id){
        for(Account list : userService.findById(id).get().getAccounts()){
            if(list.getAccountType().equals(AccountType.DOLLAR)){
                return list.getId();
            }
        }
        return  null;
    }

    public void BuyDollar(Double dollar, Long id) {
        if (dollar <= 100 && dollar > 0) {
            Account account = accountRepository.findById(id).get();
            AccountDollar accountDollar = accountDollarRepository.findById(id).get();

            Double oldSaldo = accountDollar.getSaldo();
            accountDollar.setSaldo(oldSaldo += dollar);

            Double oldSaldoPesos = account.getSaldo();
            account.setSaldo(oldSaldoPesos -= dollar);

            accountRepository.save(account);
            accountDollarRepository.save(accountDollar);

            Transfer transfer = new Transfer(findDollarAccount(id), account.getId(), dollar, "COMPRA DOLLAR", LocalDateTime.now(), account.getAlias(), account.getAlias(), oldSaldo, account.getSaldo());
            transferRepository.save(transfer);
            }
        }



    public Double cardMantenance(Long id){
            Double total = 0.0;
            Account account = accountRepository.findById(id).get();
      for(Card list:  account.getCards())  {
        switch (list.getCardType()){
            case VISA -> {
               total += 160.30;
            }
            case MAESTRO -> {
              total += 190.23;
            }
            case MARTERCARD -> {
                total += 210.40;
            }
            case AMERIACAN_EXPRESS -> {
                 total += 259.30;
            }
            case CABAL -> {
                 total += 212.30;
            }

            default -> {
                return 0.0;
            }


        }
      }
        return total;
    }

    public void monthlyCost(Long id){
            Calendar fecha = Calendar.getInstance();
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            Double saldoActual = 0.0;
        if(dia == 8) {
            cardMantenance(id);
            Account account =  accountRepository.findById(id).get();
            saldoActual = account.getSaldo() - cardMantenance(id);
            account.setSaldo(saldoActual);
            accountRepository.save(account);
        }
    }

    public Set<Card> findAllCard(Long id){
        Account account = accountRepository.findById(id).get();
        return account.getCards();
    }

    private Integer convertTypeCardToNumber(CardType type){
        Integer card = 0;
            switch (type) {
                case VISA -> {
                 return    card = 0;
                }
                case MAESTRO -> {
                return     card = 1;
                }
                case MARTERCARD -> {
                 return    card = 2;
                }
                case AMERIACAN_EXPRESS -> {
                 return    card = 3;
                }
                case CABAL -> {
                return     card = 4;
                }

                default -> {
                    return null;
                }
            }

    }
    public Card findByTypeCard(Long id, CardType type){
        Account idAccunt = accountRepository.findById(id).get();
        for(Card list : idAccunt.getCards()){
           if( list.getCardType().equals(type)){
               return  cardService.findByTypeCard(convertTypeCardToNumber(type), list.getId());
           }
        }
       return null;
    }

    public void payments(Long id, Payment payment){
        Double total = 0.0;
       Account account = accountRepository.findById(id).get();
       if(payment.getAmounts() == 1) {
           total = account.getSaldo() - payment.getTotal();
           account.setSaldo(total);
           accountRepository.save(account);
       }
    }

    public Boolean fixedTermBy30(Long id){
      Account account =  accountRepository.findByUserId(id);
      User user =  userService.findById(id).get();
       FixedTerm fixedTermByUser = fixedReository.findByUserId(user.getId());
       if(fixedTermByUser == null){
          FixedTerm fixedTerm = new FixedTerm();
          fixedTerm.setUser(user);
          fixedTerm.setAmount(account.getSaldo());
          fixedTerm.setNumberDay(30);
          fixedTerm.setState(true);
          fixedReository.save(fixedTerm);
          return true;
      }

      else if(account.getSaldo() < 100){
          return null;
      }

      else  if(fixedTermByUser.getLocalDate().compareTo(LocalDate.now())> 30 &&
              fixedReository.findByUserId(user.getId()).getState() == true){
              account.setSaldo(account.getSaldo() * 1.30);
              FixedTerm fixedTerm =   fixedTermByUser;
               fixedTerm.setState(false);
              fixedReository.save(fixedTerm);
      }

    return false;
    }
}

