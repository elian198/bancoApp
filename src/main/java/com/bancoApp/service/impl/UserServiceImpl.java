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
import java.util.Optional;
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
         User user1 =   userRepository.findById(id).get();
         user1.setSoft_delete(false);
         user1.setName(user.getName());
         user1.setLastName(user.getLastName());
         user1.setEmail(user.getEmail());
         user1.setPhone(user.getPhone());
           userRepository.save(user1);
        }
        return null;
    }

    @Override
    public void save(User user) {

        Role role = new Role();
        role.setDescription(Description.USER);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        Set<Account> accounts = new HashSet<>();
        Account pesos = new Account(AccountType.PESOS, user);
        Account dollar = new Account(AccountType.DOLLAR, user);
        pesos.setAlias(user.getName() + "." + user.getLastName());
        pesos.setSaldo(10000.0);
        dollar.setSaldo(200.0);
        accounts.add(pesos);
        accounts.add(dollar);

        if (user.getEmail().split("@")[1].equals("admin.edu")) {
            Role admin = new Role();
            admin.setDescription(Description.ADMIN);
            roleSet.add(admin);
        }
        user.setSoft_delete(false);
        user.setRoles(roleSet);
        user.setAccounts(accounts);

     userRepository.save(user);

    }

    @Override
    public void delete(Long id) {
            userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        System.out.println(userRepository.findByEmail(email));
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public User findByPhone(Integer phone)  throws PhoneNoExist{
        if(userRepository.findByPhone(phone) == null){
        //throw  new PhoneNoExist("El numero ingresado no existe");
        }
        return userRepository.findByPhone(phone);
    }

    @Override
    public Optional<User> findById(Long id) {
       if(userRepository.existsById(id)) {
           return userRepository.findByID(id);
       }
       return null;
    }

    @Override
    public List<User> findBySoftDelete() {
        return userRepository.findBySoftDelete();
    }

    @Override
    public User findByIdSoftDelete(Long id) {
        if(!userRepository.existsById(id)){
            return null;
        }
        return userRepository.findByiDAndsoftDelete(id);
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

    public String aleatorio(){
        String res = "";
        for(int i = 1; i <=6 ; i++){
            int num = (int) (Math.random()* (('z'-'a')+1))+'a';
            char letra = (char)num;
            res += letra;
        }
        return res;
    }

}
