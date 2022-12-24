package com.bancoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PhoneNoExist extends ResponseStatusException {

    public PhoneNoExist(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
