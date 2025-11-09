package com.proyecto.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proyecto.model.Atleta;
import com.proyecto.model.Deporte;
import com.proyecto.model.SesionEntrenamiento;
import com.proyecto.model.Marca;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtletaRepository {
    
    // Dependencia del DAO para la BD
    private final AtletaDAO atletaDAO;
    
    private static final String JSON_FILE = "atletas_backup.json";

    // Constructor: Inicializa el AtletaDAO
    public AtletaRepository(Connection conn) {
        // Asegúrate de que esta línea resuelva el error de 'AtletaDAO cannot be resolved'
        this.atletaDAO = new AtletaDAO(conn); 
    }

    // ======================================================
    // MÉTODOS DE PERSISTENCIA EN JSON (Carga y Guardado)
    // ======================================================

    public List<Atleta> cargarDesdeJSON() {
        // ... (Tu lógica de carga JSON existente) ...
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Atleta>>() {}.getType();
            List<Atleta> atletas = gson.fromJson(reader, listType);
            return atletas != null ? atletas : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("⚠️ No se encontró el archivo JSON. Se inicia lista vacía.");
            return new ArrayList<>();
        }
    }
    
    public void guardarEnJSON(List<Atleta> listaAtletas) {
        // ... (Tu lógica de guardado JSON existente) ...
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            Gson gson = new Gson();
            gson.toJson(listaAtletas, writer);
        } catch (IOException e) {
            System.err.println("❌ Error al guardar en JSON: " + e.getMessage());
        }
    }
    
    // ======================================================
    // MÉTODOS DE PERSISTENCIA EN BASE DE DATOS (BD)
    // ======================================================

    /**
     * Lógica para guardar o actualizar un solo atleta en la BD.
     * @param atleta El atleta a guardar.
     * @throws SQLException 
     */
    public void guardar(Atleta atleta) throws SQLException {
        this.atletaDAO.guardar(atleta);
        // NOTA: Si el método guardar del DAO devuelve un ID,
        // Aquí se actualizaría el ID del objeto Atleta.
    }

    /**
     * Implementación para la Opción 4: Guarda todos los atletas en la BD.
     * Delega la responsabilidad de guardado al DAO.
     * @param atletas La lista de atletas a guardar.
     * @return El número de atletas procesados.
     * @throws SQLException 
     */
    public int guardarTodosEnBD(List<Atleta> atletas) throws SQLException { 
        int contador = 0;
        for (Atleta atleta : atletas) {
            // Llama al método de guardado/actualización del DAO para cada atleta
            this.atletaDAO.guardar(atleta);
            contador++;
        }
        return contador; 
    }
    
    /**
     * Implementación para la Opción 4: Carga todos los atletas desde la BD.
     * Delega la responsabilidad de obtención al DAO.
     * @return La lista de atletas cargados.
     * @throws SQLException 
     */
    public List<Atleta> obtenerTodosDesdeBD() throws SQLException { 
        // Llama al método obtenerTodos del DAO. 
        // El DAO se encarga del mapeo básico de las columnas de la tabla Atleta.
        return this.atletaDAO.obtenerTodos(); 
        
        // NOTA: Para una aplicación completa, aquí se debe asegurar 
        // que las Sesiones y Marcas también se carguen y se añadan 
        // a cada objeto Atleta.
    }
    
    // ======================================================
    // OTROS MÉTODOS DEL REPOSITORIO (Si los necesitas)
    // ======================================================
    
    // public Atleta buscarPorId(int id) { ... }
    // public void eliminar(Atleta atleta) { ... }
}