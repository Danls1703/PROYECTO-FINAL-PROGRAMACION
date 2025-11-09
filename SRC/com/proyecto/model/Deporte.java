package com.proyecto.model; // Asumiendo que esta es la carpeta de modelos

public class Deporte {
    
    private String nombre;
    private String especialidad;
    private String tipo;

    /** Constructor vacío */
    public Deporte() {}

    /** Constructor completo */
    public Deporte(String nombre, String especialidad, String tipo) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.tipo = tipo;
    }

    // --- Getters y Setters ---
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    } 
}
