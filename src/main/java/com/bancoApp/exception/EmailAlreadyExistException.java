package com.bancoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExistException extends ResponseStatusException {

    public EmailAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
