package com.bancoApp.dto;

public class AuthTokenDto {

    private String token;

    public AuthTokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
