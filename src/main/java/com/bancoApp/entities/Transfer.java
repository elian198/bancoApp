package com.bancoApp.entities;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSFER")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_EMISOR")
    private Long idEmisor;

    @Column(name = "ID_REMITENTE")
    private Long idRemitente;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE_TIME")
    private LocalDateTime localDateTime;



    @Column(name = "ALIAS_EMISOR")
    private String aliasEmisor;

    @Column(name = "ALIAS_RECEPTOR")
    private String aliasReceptor;

    @Column(name = "OLD_BALANCE")
    private Double oldBalance;

    @Column(name = "CURRENT_BALANCE")
    private double currentBalance;


    public Transfer() { }

    public Transfer( Long idEmisor, Long idRemitente, Double amount, String description, LocalDateTime localDateTime, String aliasEmisor, String aliasReceptor, Double oldBalance, double currentBalance) {
        this.idEmisor = idEmisor;
        this.idRemitente = idRemitente;
        this.amount = amount;
        this.description = description;
        this.localDateTime = localDateTime;
        this.aliasEmisor = aliasEmisor;
        this.aliasReceptor = aliasReceptor;
        this.oldBalance = oldBalance;
        this.currentBalance = currentBalance;
    }

    public Long getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Long idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getAliasEmisor() {
        return aliasEmisor;
    }

    public void setAliasEmisor(String aliasEmisor) {
        this.aliasEmisor = aliasEmisor;
    }

    public String getAliasReceptor() {
        return aliasReceptor;
    }

    public void setAliasReceptor(String aliasReceptor) {
        this.aliasReceptor = aliasReceptor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(Long idRemitente) {
        this.idRemitente = idRemitente;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Double getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(Double oldBalance) {
        this.oldBalance = oldBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", idEmisor=" + idEmisor +
                ", idRemitente=" + idRemitente +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", localDateTime=" + localDateTime +
                ", aliasEmisor='" + aliasEmisor + '\'' +
                ", aliasReceptor='" + aliasReceptor + '\'' +
                ", oldBalance=" + oldBalance +
                ", currentBalance=" + currentBalance +
                '}';
    }
}
