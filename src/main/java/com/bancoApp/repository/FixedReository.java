package com.bancoApp.repository;

import com.bancoApp.entities.Account;
import com.bancoApp.entities.FixedTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FixedReository extends JpaRepository<FixedTerm, Long> {

    @Query(value = "SELECT * FROM fixedTerms WHERE fixedTerms.user_id  = :filtro" ,nativeQuery = true)
    FixedTerm findByUserId(@Param("filtro") Long id);
}
