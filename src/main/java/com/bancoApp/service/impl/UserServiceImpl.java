package com.bancoApp.service.impl;

import com.bancoApp.entities.Account;
import com.bancoApp.entities.Role;
import com.bancoApp.entities.User;
import com.bancoApp.entities.enums.AccountType;
import com.bancoApp.entities.enums.Description;
import com.bancoApp.exception.EmailAlreadyExistException;
import com.bancoApp.exception.PhoneNoExist;
import com.bancoApp.exception.UserNameAlreadyExistsException;
import com.bancoApp.repository.RoleRepository;
import com.bancoApp.repository.UserRepository;
import com.bancoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User update(Long id, User user) {
        if(userRepository.existsById(id)){
           userRepository.save(user);
        }
        return null;
    }

    @Override
    public void save(User user) {

        Role role = new Role();
        role.setDescription(Description.USER);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if (user.getEmail().split("@")[1].equals("admin.edu")) {
            role.setDescription(Description.ADMIN);
            roleSet.add(role);
        }
        Set<Account> accounts = new HashSet<>();
        Account pesos = new Account(AccountType.PESOS);
        Account dollar = new Account(AccountType.DOLLAR);
        accounts.add(pesos);
        accounts.add(dollar);

     userRepository.save(user);

    }

    @Override
    public List<User> delete(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
        return  null;
    }

    @Override
    public User findByEmail(String email) {
        if(userRepository.findByEmail(email) == null){
            throw new EmailAlreadyExistException("El email no existe!!");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUserName(String name) {
        if(userRepository.findByUserName(name)== null ){
            throw new UserNameAlreadyExistsException("El usuario que ingreso no existe");
        }
        return userRepository.findByUserName(name);
    }

    @Override
    public User findByPhone(Integer phone)  throws PhoneNoExist{
        if(userRepository.findByPhone(phone) == null){
        throw  new PhoneNoExist("El numero ingresado no existe");
        }
        return userRepository.findByPhone(phone);
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

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getDescription()));
        });
        return authorities;
    }
    public List<User> findAll(){
        return  userRepository.findAll();
    }


}
