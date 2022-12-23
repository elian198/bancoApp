package com.bancoApp.service;

import com.bancoApp.entities.User;

import java.util.List;

public interface UserService {

    void save(User user);
    User update(User user);
    List<User> delete(Long id);
    User findByEmail(String email);
    User findByUserName(String name);
    User findByPhone(Integer phone);
    User findById(Long id);
}
