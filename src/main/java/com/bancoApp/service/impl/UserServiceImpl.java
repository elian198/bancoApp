package com.bancoApp.service.impl;

import com.bancoApp.entities.User;
import com.bancoApp.repository.UserRepository;
import com.bancoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public List<User> delete(Long id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User findByUserName(String name) {
        return null;
    }

    @Override
    public User findByPhone(Integer phone) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.bancoApp.entities.User user = userRepository.findByUserName(username);

        return UserDetailsMapper.build(user);
    }

}
