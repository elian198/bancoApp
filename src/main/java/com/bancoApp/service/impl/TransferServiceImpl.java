package com.bancoApp.service.impl;

import com.bancoApp.entities.Transfer;
import com.bancoApp.repository.TransferRepository;
import com.bancoApp.service.TrasferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements TrasferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountServiceImpl accountService;

    public TransferServiceImpl(TransferRepository transferRepository, AccountServiceImpl accountService) {
        this.transferRepository = transferRepository;
        this.accountService = accountService;
    }

    @Override
    public List<Transfer> findById(Long id) {
        Long idAccount = accountService.findById(id).getId();
        return transferRepository.findById(idAccount, idAccount);
    }

    @Override
    public List<Transfer> findByAlian(String alias) {

        return transferRepository.findByAlias(alias);
    }

    @Override
    public List<Transfer> findOrderByDate() {
        return null;
    }

    @Override
    public List<Transfer> findOrderByDescrpiton(Long id, String description) {
        return  transferRepository.orderByDescription(description, id);
    }


    }

