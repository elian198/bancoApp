package com.bancoApp.repository;

import com.bancoApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE users.email = :filtro" ,nativeQuery = true)
    User findByEmail(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM users WHERE users.name = :filtro" ,nativeQuery = true)
    User findByUserName(@Param("filtro") String filtro);

    @Query(value = "SELECT * FROM users WHERE users.phone = :filtro" ,nativeQuery = true)
    User findByPhone(@Param("filtro") Integer filtro);

    @Query(value = "SELECT * FROM users WHERE users.id = :filtro" ,nativeQuery = true)
    Optional<User> findByID(@Param("filtro") Long filtro);

    @Query(value = "SELECT * FROM users WHERE users.soft_delete = true" ,nativeQuery = true)
    List<User> findBySoftDelete();

    @Query(value = "SELECT * FROM users WHERE users.soft_delete = false AND users.id = :filtro" ,nativeQuery = true)
    User findByiDAndsoftDelete(@Param("filtro") Long id);
}
