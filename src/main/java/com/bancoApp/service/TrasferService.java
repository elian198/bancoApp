package com.bancoApp.service;

import com.bancoApp.entities.Transfer;

import java.util.List;

public interface TrasferService {

    List<Transfer> findById(Long id);
    List<Transfer> findByAlian(String alias);
    List<Transfer> findOrderByDate();
    List<Transfer> findOrderByDescrpiton(Long id, String description);

}
