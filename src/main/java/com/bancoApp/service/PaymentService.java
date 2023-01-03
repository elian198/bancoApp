package com.bancoApp.service;

import com.bancoApp.entities.Payment;

public interface PaymentService {

    Payment findById(Long id);

}
