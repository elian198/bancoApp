package com.bancoApp.controller;

import com.bancoApp.dto.TransferDto;
import com.bancoApp.entities.User;
import com.bancoApp.entities.enums.AccountType;
import com.bancoApp.service.impl.AccountServiceImpl;
import com.bancoApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;


    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    public AccountController(AccountServiceImpl accountService, AuthenticationManager authenticationManager, UserServiceImpl userService) {
        this.accountService = accountService;
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

        if(accountService.findById(user.getIdUser()).getAccountType().equals(AccountType.PESOS) && accountService.findById(user.getIdUser()).getSaldo() < transferDto.getSaldo() ){
            return ResponseEntity.badRequest().body("NO TIENE SALDO SUFICIENTE!!\n" +
                    "SALDO ACTUAL: $" +accountService.findById(user.getIdUser()).getSaldo());
        }

         accountService.transfer(auth.getName(),  transferDto.getIdSender(), transferDto.getSaldo());
        return ResponseEntity.ok("ok");
    }


}
