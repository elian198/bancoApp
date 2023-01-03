package com.bancoApp.controller;

import com.bancoApp.dto.TransferDto;
import com.bancoApp.entities.*;
import com.bancoApp.entities.enums.AccountType;
import com.bancoApp.entities.enums.CardType;
import com.bancoApp.repository.FixedReository;
import com.bancoApp.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private TransferServiceImpl transferService;

    @Autowired
    private CardServiceImpl cardService;

    @Autowired
    private FixedReository fixedReository;

    @Autowired
    private AccountDollarServiceImpl accountDollarService;

    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    public AccountController(AccountServiceImpl accountService, TransferServiceImpl transferService,
                             CardServiceImpl cardService, FixedReository fixedReository,
                             AccountDollarServiceImpl accountDollarService, AuthenticationManager
                                     authenticationManager, UserServiceImpl userService) {
        this.accountService = accountService;
        this.transferService = transferService;
        this.cardService = cardService;
        this.fixedReository = fixedReository;
        this.accountDollarService = accountDollarService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto) {
      FixedTerm fixedTerm =  fixedReository.findByUserId(date().getId());
        if(fixedTerm != null){
            if (fixedTerm.getState() == true) {
                return ResponseEntity.badRequest().body("Usted tiene un plazo fijo no pueder utilizar el dinero hasta el dia: " + fixedTerm.getLocalDate().plusDays(30));
            }
            if (accountService.findById(transferDto.getIdSender()) == null) {
                return ResponseEntity.badRequest().body("El id del usuario no existe");
            }

            if (accountService.findById(transferDto.getIdSender()) == null) {
                return ResponseEntity.badRequest().body("ERROR EL ID DE LA CUENTA NO EXISTE!!");
            }
            if (transferDto.getSaldo() < 1 || transferDto.getSaldo() > 10000) {
                return ResponseEntity.badRequest().body("EL SALDO TIENE QUE SER MAYOR A $0\nNO PUEDE SER MAYOR A $10000");
            }


            if (userService.findByIdSoftDelete(date().getId()).getSoft_delete() == true) {
                return ResponseEntity.badRequest().body("Acceso denegado, su cuenta esta bloqueada!!");
            }

            if (accountService.findById(date().getId()).getAccountType().equals(AccountType.PESOS) && accountService.findById(date().getId()).getSaldo() < transferDto.getSaldo()) {
                return ResponseEntity.badRequest().body("NO TIENE SALDO SUFICIENTE!!\n" +
                        "SALDO ACTUAL: $" + accountService.findById(date().getId()).getSaldo());
            }
        }
        accountService.transfer(date().getName(), transferDto.getIdSender(), transferDto.getSaldo());
        return ResponseEntity.ok("Transferencia realizada con exito");
    }


    @GetMapping("/transfers")
    public ResponseEntity<?> findById() {
        return ResponseEntity.ok(transferService.findById(date().getId()));
    }

    @PostMapping("/transfer/{description}")
    public ResponseEntity<List<Transfer>> findByDescription(@PathVariable String description) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = userService.findByUserName(auth.getName()).getId();
        return ResponseEntity.ok(transferService.findOrderByDescrpiton(id, description));
    }

    @PostMapping("/transfer/buyDollar/{dollar}")
    public ResponseEntity<?> buyDollar(@PathVariable Double dollar) {
        if (dollar > 100 ) {
            return ResponseEntity.badRequest().body("No se puede comprar mas de u$s100 dollar por dia");
        }
        if ( accountDollarService.buyByDay(dollar) > 100){
            return ResponseEntity.badRequest().body("No se puede comprar mas de u$s100 dollar por dia");
        }

        if (userService.findByIdSoftDelete(date().getId()) != null) {
            return ResponseEntity.badRequest().body("Acceso denegado, Usuario bloqueado!!");
        }
        accountService.BuyDollar(dollar, date().getId());
        return ResponseEntity.ok(" La compra de dolar fue realizada");
    }

    @PostMapping("/addCard")
    public ResponseEntity<?> addCard(@RequestBody Card card) {

        if (accountService.countCards(date().getId()) >3) {
            return ResponseEntity.badRequest().body("Ya tiene 3 tarjetas " + card.getCardType());
        }
        if (cardService.existCardNumber(card.getCardNumber())) {
            return ResponseEntity.badRequest().body("EL numero de tarjeta ya existe, elija otro");
        }

        if (accountService.existTypeCard(date().getId(), card.getCardType())) {
            return ResponseEntity.badRequest().body("Ya tiene una tarjeta " + card.getCardType());
        }
        if (card.getExpirationDate().compareTo(LocalDate.now()) <0) {
            return ResponseEntity.badRequest().body("No puede poner una fecha que ya paso\n" +
                    "Fecha actual: " +LocalDate.now());
        }

        accountService.addCard(date().getId(), card);
        return ResponseEntity.ok("Tarjeta " + card.getCardType() + " agregada");
    }

    @GetMapping("/cards")
    public ResponseEntity<?> findByCards() {
        return ResponseEntity.ok(accountService.findAllCard(date().getId()));
    }

    @PostMapping("/cards/{typeCard}")
    public ResponseEntity<?> findByCards(@PathVariable CardType typeCard) {
        if(!accountService.existTypeCard(date().getId(), typeCard)) {
            return ResponseEntity.badRequest().body("LA TARJETA INGRESADA NO SE ENCUENTRA");
        }
        System.out.println(accountService.findByTypeCard(date().getId(), typeCard));
        return ResponseEntity.ok(accountService.findByTypeCard(date().getId(), typeCard));
    }
    @PostMapping("/cards/addPayment/{id}")
    public ResponseEntity<?> addPayment(@RequestBody Payment payment, @PathVariable Long id) {
        Account account = accountService.findById(date().getId());

     if(account.getSaldo() < payment.getTotal()){
         return ResponseEntity.badRequest().body("Saldo insoficiente!!\nSaldo actual es:" +
                 account.getSaldo());
     }
        if(cardService.expenses(id)  > account.getSaldo()){
            return ResponseEntity.badRequest().body("Supera el limite de saldo disponible!!" +
                    "\nSaldo disponible es: " +
                    (account.getSaldo() - cardService.expenses(id) + payment.getTotal()));
        }
        Card card = cardService.findById(id);
        if(card.getExpirationDate().compareTo(LocalDate.now()) < 0){
            return ResponseEntity.badRequest().body("Tajeta venciada!!\n" +
                    "Su Vencimiento es: " + card.getExpirationDate());

        }
        if(payment.getAmounts() >12){
            return ResponseEntity.badRequest().body("Maximo de cuostas permitidas:  12");

        }
        accountService.payments(account.getId(), payment);
        cardService.addPayment(id, payment);
        return ResponseEntity.ok( "Pago realizado" );
    }

    private User date(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      return   userService.findByUserName(auth.getName());
    }

    @GetMapping("/dollarCotization")
    public ResponseEntity<?> dollarCotization(){
        String url = "https://www.dolarsi.com/api/api.php?type=valoresprincipales";
        ResponseEntity<String> responseEntity= new RestTemplate().getForEntity(url, String.class);
    return ResponseEntity.ok(responseEntity.getBody());
    }

    @GetMapping("/fixedTerm")
    public ResponseEntity<?> fixedTerm(){

        FixedTerm fixedTerm = fixedReository.findByUserId(date().getId());
        if(fixedTerm != null) {
            if (fixedTerm.getState() == true) {
                return ResponseEntity.badRequest().body("Tu cuenta ya tiene un plazo fijo");
            }
            if (accountService.fixedTermBy30(date().getId()) == null) {
                return ResponseEntity.badRequest().body("No tienes saldo suficiente para realizar un plazo fijo");
            }

            if (fixedTerm.getLocalDate().compareTo(LocalDate.now()) < 30 &&
                    fixedTerm.getLocalDate().compareTo(LocalDate.now()) > 0) {
                return ResponseEntity.badRequest().body("Tu cuenta ya tiene un plazo fijo");
            }
        }
        accountService.fixedTermBy30(date().getId());
        return ResponseEntity.ok("Plazo fijo realizado podra usar el efectivo apartir del :" + LocalDate.now().plusDays(30));

    }

}

