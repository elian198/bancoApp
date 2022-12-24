package com.bancoApp.dto;

public class TransferDto {

    private Long idSender;
    private Double saldo;

    public TransferDto() { }


    public Long getIdSender() {
        return idSender;
    }

    public void setIdSender(Long idSender) {
        this.idSender = idSender;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
