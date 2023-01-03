package com.bancoApp.repository;

import com.bancoApp.entities.Account;
import com.bancoApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM accounts WHERE accounts.user_id  = :filtro" ,nativeQuery = true)
    Account findByUserId(@Param("filtro") Long id);
}
