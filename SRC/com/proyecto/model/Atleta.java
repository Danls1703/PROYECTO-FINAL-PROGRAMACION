package com.proyecto.model;

import java.util.ArrayList;
import java.util.List;

public class Atleta {
    
    // --- Atributos ---
    private int id;
    private String nombre;
    private String apellido; 
    private int edad;
    private double mejorMarcaPersonal;
    private String pais;
    private Deporte deporte;
    private List<SesionEntrenamiento> sesiones;
    private List<Marca> marcas;
    private boolean esExtranjero; // <--- CAMPO REQUERIDO PARA EL DAO/SERVICE

    // --- Constructor vacío ---
    public Atleta() {
        this.sesiones = new ArrayList<>();
        this.marcas = new ArrayList<>();
    }

    // --- Constructor completo (8 argumentos) ---
    public Atleta(int id, String nombre, String apellido, int edad, double mejorMarcaPersonal, String pais, Deporte deporte, boolean esExtranjero) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.mejorMarcaPersonal = mejorMarcaPersonal;
        this.pais = pais;
        this.deporte = deporte;
        this.esExtranjero = esExtranjero; // <--- INCLUIDO EN EL CONSTRUCTOR
        this.sesiones = new ArrayList<>();
        this.marcas = new ArrayList<>();
    }
    
    // --- Constructor de 4 argumentos ---
    public Atleta(String nombre, String apellido, String pais, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.pais = pais;
        this.edad = edad;
        this.esExtranjero = false; // Valor por defecto
        this.sesiones = new ArrayList<>();
        this.marcas = new ArrayList<>();
    }

    // --- Getters y Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    
    public double getMejorMarcaPersonal() { return mejorMarcaPersonal; }
    public void setMejorMarcaPersonal(double mejorMarcaPersonal) { this.mejorMarcaPersonal = mejorMarcaPersonal; }
    
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    
    public Deporte getDeporte() { return deporte; }
    public void setDeporte(Deporte deporte) { this.deporte = deporte; }
    
    public List<SesionEntrenamiento> getSesiones() { return sesiones; }
    public void setSesiones(List<SesionEntrenamiento> sesiones) { this.sesiones = sesiones; }
    
    public List<Marca> getMarcas() { return marcas; }
    public void setMarcas(List<Marca> marcas) { this.marcas = marcas; }

    // --- CORRECCIÓN CRÍTICA: Getter y Setter para 'esExtranjero' ---
    /** * El compilador espera isEsExtranjero() para booleanos.
     * Si usaras un tipo diferente, el getter sería getEsExtranjero().
     * Usamos 'is' para cumplir con la convención estándar que el DAO estaba usando.
     */
    public boolean isEsExtranjero() {
        return esExtranjero;
    }

    public void setEsExtranjero(boolean esExtranjero) {
        this.esExtranjero = esExtranjero;
    }
    // --- FIN CORRECCIÓN CRÍTICA ---

    public void agregarSesion(SesionEntrenamiento sesion) {
        this.sesiones.add(sesion);
    }
}
