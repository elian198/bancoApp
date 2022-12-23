package com.bancoApp.repository;

import com.bancoApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE user.email = :filtro" ,nativeQuery = true)
    User findByEmail(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM user WHERE user.name = :filtro" ,nativeQuery = true)
    User findByUserName(@Param("filtro") String filtro);

}
