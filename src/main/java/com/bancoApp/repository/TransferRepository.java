package com.bancoApp.repository;

import com.bancoApp.entities.Transfer;
import com.bancoApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query(value = "SELECT * FROM transfer WHERE transfer.id_emisor = :filtro OR transfer.id_remitente = :id_remitente" ,nativeQuery = true)
    List<Transfer> findById(@Param("filtro")Long id, @Param("id_remitente")Long idRemitente);


    @Query(value = "SELECT * FROM transfer WHERE transfer.id_emisor = :filtro order by date_time" ,nativeQuery = true)
    List<Transfer> orderByDateTime(@Param("filtro")Long id);

    @Query(value = "SELECT * FROM transfer WHERE transfer.alias = :filtro" ,nativeQuery = true)
    List<Transfer> findByAlias(@Param("filtro")String alias);

    @Query(value = "SELECT * FROM transfer WHERE transfer.description = :filtro AND transfer.id_emisor= :id" ,nativeQuery = true)
    List<Transfer> orderByDescription(@Param("filtro")String description, @Param("id")Long id);
}

