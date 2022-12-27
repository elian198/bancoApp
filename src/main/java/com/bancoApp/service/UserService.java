package com.bancoApp.service;

import com.bancoApp.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);
    User update(Long id, User user);
    void delete(Long id);
    User findByEmail(String email);
    User findByUserName(String name);
    User findByPhone(Integer phone);
    Optional<User> findById(Long id);
}
