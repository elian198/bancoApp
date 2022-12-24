package com.bancoApp.controller;

import com.bancoApp.entities.User;
import com.bancoApp.security.payload.LoginPayload;
import com.bancoApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPayload loginPayload){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginPayload.getName() ,
                        loginPayload.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok("Usuario creado");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        if(userService.findByUserName(user.getName()) != null){
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        if(userService.findByEmail(user.getEmail()) != null){
            return ResponseEntity.badRequest().body("El Email ya existe");
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userService.save(user);

        return ResponseEntity.ok("Usuario creado");
    }
}
