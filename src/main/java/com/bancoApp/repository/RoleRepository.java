package com.bancoApp.repository;


import com.bancoApp.entities.Role;
import com.bancoApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM roles WHERE roles.description = :filtro" ,nativeQuery = true)
    Role findByDescription(@Param("filtro") String filtro);

}
