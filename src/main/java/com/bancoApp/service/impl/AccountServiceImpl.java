package com.bancoApp.service.impl;

import com.bancoApp.dto.DollarCotization;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

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

                  Long accountpesos = findPesosAccount(user.getId());
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

    public Long findPesosAccount(Long id){
        for(Account list : userService.findById(id).get().getAccounts()){
            if(list.getAccountType().equals(AccountType.PESOS)){
                return list.getId();
            }
        }
        return  null;
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
            Long idAccount = findDollarAccount(id);
            Long idAccountPesos = findPesosAccount(id);

            Double saldoDollar = accountRepository.findById(idAccount).get().getSaldo();
            Double saldoPesos = accountRepository.findById(idAccountPesos).get().getSaldo();


            Account account = accountRepository.findById(idAccount).get();
            Double oldSaldo = account.getSaldo();
            account.setSaldo(saldoDollar += dollar);


            Account accountPesos = accountRepository.findById(idAccountPesos).get();
            Double oldSaldoPesos = accountPesos.getSaldo();
            account.setSaldo(saldoPesos -= dollar);

            accountRepository.save(account);
            accountRepository.save(accountPesos);

            Transfer transfer = new Transfer(findDollarAccount(id) , idAccountPesos, dollar, "COMPRA DOLLAR", LocalDateTime.now(), accountPesos.getAlias(),account.getAlias(), oldSaldo, account.getSaldo() );
            transferRepository.save(transfer);


            ResponseEntity<Object> response = restTemplate.getForEntity("https://www.dolarsi.com/api/api.php?type=valoresprincipales", Object.class);
            System.out.println(response.getBody());
        }
        return;
    }
}

