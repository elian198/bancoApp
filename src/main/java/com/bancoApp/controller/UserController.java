package com.bancoApp.controller;

import com.bancoApp.dto.AuthTokenDto;
import com.bancoApp.entities.Role;
import com.bancoApp.entities.User;
import com.bancoApp.security.jwt.JwtTokenUtil;
import com.bancoApp.security.payload.LoginPayload;
import com.bancoApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    private AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPayload loginPayload){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginPayload.getName() ,
                        loginPayload.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateJwtToken(authentication);
        UserDetails  userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new AuthTokenDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        if(userService.findByUserName(user.getName()) != null){
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        if(userService.findByEmail(user.getEmail()) != null){
            return ResponseEntity.badRequest().body("El Email ya existe");
        }

        if(userService.findByPhone(user.getPhone()) != null){
            return ResponseEntity.badRequest().body("El telefono ya existe!!");
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userService.save(user);

        return ResponseEntity.ok("Usuario creado");
    }


    @PutMapping("/user")
    public ResponseEntity<?> update(@RequestBody User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = userService.findByUserName(auth.getName()).getId();
        if(userService.findByUserName(auth.getName()) == null) {
            return ResponseEntity.badRequest().body("No esta logueado");
        }
        if(userService.findByIdSoftDelete(id) == null){
           return ResponseEntity.badRequest().body("acceso denegado, Usuario bloqueado!!");
        }

        userService.update(id, user);
        return ResponseEntity.ok("Usuario: " + auth.getName() + " Modificado");
    }



}
