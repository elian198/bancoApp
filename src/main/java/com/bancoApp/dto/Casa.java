package com.bancoApp.dto;

import java.util.ArrayList;
import java.util.List;

public class Casa {

    private Double compra;
    private Double venta;
    private Integer agencia;
    private String nombre;

    private int variacion;
    private Boolean ventaCero;
    private float decimal;

    public Casa() { }

    public Double getCompra() {
        return compra;
    }

    public void setCompra(Double compra) {
        this.compra = compra;
    }

    public Double getVenta() {
        return venta;
    }

    public void setVenta(Double venta) {
        this.venta = venta;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getVentaCero() {
        return ventaCero;
    }

    public void setVentaCero(Boolean ventaCero) {
        this.ventaCero = ventaCero;
    }


    public float getDecimal() {
        return decimal;
    }

    public void setDecimal(float decimal) {
        this.decimal = decimal;
    }

    public int getVariacion() {
        return variacion;
    }

    public void setVariacion(int variacion) {
        this.variacion = variacion;
    }

    @Override
    public String toString() {
        return "DollarCotization{" +
                "compra=" + compra +
                ", venta=" + venta +
                ", agencia=" + agencia +
                ", nombre='" + nombre + '\'' +
                ", ventaCero=" + ventaCero +
                ", decimal=" + decimal +
                '}';
    }
}
