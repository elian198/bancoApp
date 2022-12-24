package com.bancoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNameAlreadyExistsException extends ResponseStatusException {

    public UserNameAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
