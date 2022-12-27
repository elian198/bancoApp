package com.bancoApp.dto;

public class DollarCotization {

    private Double compra;
    private Double venta;
    private Integer agencia;
    private String nombre;
    private Boolean ventaCero;
    private Double decimal;

    public DollarCotization() { }

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

    public Double getDecimal() {
        return decimal;
    }

    public void setDecimal(Double decimal) {
        this.decimal = decimal;
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
