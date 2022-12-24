package com.bancoApp.entities;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENTS")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Double total;

    @Column(name = "amounts")
    private Integer amounts;

    public Payment() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", total=" + total +
                ", amounts=" + amounts +
                '}';
    }
}


