package com.bancoApp.repository;

import com.bancoApp.entities.AccountDollar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDollarRepository extends JpaRepository<AccountDollar, Long> {
}
