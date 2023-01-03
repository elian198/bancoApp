package com.bancoApp.dto;

import java.util.ArrayList;
import java.util.List;

public class DollarCotization {

    private List<Casa> casas = new ArrayList<>();

    public DollarCotization() {}

    public List<Casa> getCasas() {
        return casas;
    }

    public void setCasas(List<Casa> casas) {
        this.casas = casas;
    }

    @Override
    public String toString() {
        return "DollarCotization{" +
                "casas=" + casas +
                '}';
    }
}
