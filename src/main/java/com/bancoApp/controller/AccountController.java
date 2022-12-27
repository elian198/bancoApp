package com.bancoApp.controller;

import com.bancoApp.dto.TransferDto;
import com.bancoApp.entities.Transfer;
import com.bancoApp.entities.User;
import com.bancoApp.entities.enums.AccountType;
import com.bancoApp.repository.TransferRepository;
import com.bancoApp.service.impl.AccountServiceImpl;
import com.bancoApp.service.impl.TransferServiceImpl;
import com.bancoApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private TransferServiceImpl transferService;

    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    public AccountController(AccountServiceImpl accountService, TransferServiceImpl transferService, AuthenticationManager authenticationManager, UserServiceImpl userService) {
        this.accountService = accountService;
        this.transferService = transferService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(accountService.findById(transferDto.getIdSender()) == null){
            return ResponseEntity.badRequest().body("El id del usuario no existe");
        }

        if(accountService.findById(transferDto.getIdSender()) == null){
            return ResponseEntity.badRequest().body("ERROR EL ID DE LA CUENTA NO EXISTE!!");
        }
        if(transferDto.getSaldo() < 1 || transferDto.getSaldo() >10000){
            return ResponseEntity.badRequest().body("EL SALDO TIENE QUE SER MAYOR A $0\nNO PUEDE SER MAYOR A $10000");
        }
        User user = userService.findByUserName(auth.getName());

        if(accountService.findById(user.getId()).getAccountType().equals(AccountType.PESOS) && accountService.findById(user.getId()).getSaldo() < transferDto.getSaldo() ){
            return ResponseEntity.badRequest().body("NO TIENE SALDO SUFICIENTE!!\n" +
                    "SALDO ACTUAL: $" +accountService.findById(user.getId()).getSaldo());
        }

         accountService.transfer(auth.getName(),  transferDto.getIdSender(), transferDto.getSaldo());
        return ResponseEntity.ok("ok");
    }


        @GetMapping("/transfers")
        public ResponseEntity<List<Transfer>> findById(){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long id = userService.findByUserName(auth.getName()).getId();
            return   ResponseEntity.ok(transferService.findById(id));
        }

    @PostMapping("/transfer/{description}")
    public ResponseEntity<List<Transfer>> findByDescription(@PathVariable String description){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = userService.findByUserName(auth.getName()).getId();
        return   ResponseEntity.ok(transferService.findOrderByDescrpiton(id, description));
    }

    @PostMapping("/transfer/buyDollar/{dollar}")
    public ResponseEntity<?> buyDollar(@PathVariable Double dollar){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(dollar > 100){
            return ResponseEntity.badRequest().body("No se puede comprar mas de u$s100 dollar por dia");
        }
        Long id = userService.findByUserName(auth.getName()).getId();
        accountService.BuyDollar(dollar, id);
        return   ResponseEntity.ok(" La compra de dolar fue realizada");
      }

    }

