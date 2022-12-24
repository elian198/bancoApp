package com.bancoApp.security.payload;

public class LoginPayload {

    private String name;
    private String password;

    public LoginPayload() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
