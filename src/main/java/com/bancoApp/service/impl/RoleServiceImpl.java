package com.bancoApp.service.impl;

import com.bancoApp.entities.Role;
import com.bancoApp.repository.RoleRepository;
import com.bancoApp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name) {
        return
                roleRepository.findByDescription(name);
    }
}
