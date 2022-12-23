package com.bancoApp.controller;

import com.bancoApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok("Usuario creado");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(){
        return ResponseEntity.ok("Usuario creado");
    }
}
