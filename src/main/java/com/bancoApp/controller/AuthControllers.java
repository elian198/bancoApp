package com.bancoApp.controller;

import com.bancoApp.dto.CardDto;
import com.bancoApp.entities.Card;
import com.bancoApp.entities.User;
import com.bancoApp.service.impl.CardServiceImpl;
import com.bancoApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AuthControllers {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CardServiceImpl cardService;

    public AuthControllers(UserServiceImpl userService, CardServiceImpl cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }



    @PostMapping("/users/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        return ResponseEntity.ok(userService.findById(id));
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(userService.findById(id) == null){
            return ResponseEntity.badRequest().body("El id ingresado no existe!!!");
        }
        userService.delete(id);
        return ResponseEntity.ok(  "Usuario eliminado");
    }

    @GetMapping("/users/bloqued")
    public ResponseEntity<List<User>> findBySoftDelete(){
        return ResponseEntity.ok(  userService.findBySoftDelete());
    }

    @PostMapping("/users/unlock/{id}")
    public ResponseEntity<?> unlockById(@PathVariable Long id){
        if(userService.findByIdSoftDelete(id)== null){
            return ResponseEntity.badRequest().body("El id no existe!!");
        }
        userService.unLockById(id);
        return ResponseEntity.ok("Usuario desbloqueado");
    }

    @GetMapping("/users/cards")
    public ResponseEntity<List<Card>> orderByDate(){

        return ResponseEntity.ok( cardService.orderByExpirationDate() );
    }

}
