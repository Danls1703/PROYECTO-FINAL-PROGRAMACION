package com.proyecto.model; // Asumiendo que esta es tu carpeta de modelos

import java.util.Date;

public class Marca {
    
    // --- Atributos ---
    private double valor;
    private String unidad;
    private Date timestamp;

    // --- Constructor Vacío ---
    public Marca() {
    }

    // --- Constructor Completo (Usado en AtletaService) ---
    public Marca(double valor, String unidad, Date timestamp) {
        this.valor = valor;
        this.unidad = unidad;
        this.timestamp = timestamp;
    }

    // --- Getters y Setters ---
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}