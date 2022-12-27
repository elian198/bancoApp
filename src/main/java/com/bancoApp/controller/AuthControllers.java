package com.bancoApp.controller;

import com.bancoApp.entities.User;
import com.bancoApp.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AuthControllers {

    @Autowired
    private UserServiceImpl userService;

    public AuthControllers(UserServiceImpl userService) {
        this.userService = userService;
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
}
