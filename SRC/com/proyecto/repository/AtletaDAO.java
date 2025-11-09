package com.proyecto.repository;

import com.proyecto.model.Atleta;
import com.proyecto.model.Deporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AtletaDAO {
    
    private final Connection connection;

    public AtletaDAO(Connection connection) {
        this.connection = connection;
    }

    /** Implementación: GUARDAR (INSERT) un nuevo atleta */
    public void guardar(Atleta atleta) throws SQLException {
        // CORRECCIÓN SQL: Usamos nombre_deporte, apellido, mejor_marca_personal, es_extranjero
        String sql = "INSERT INTO atletas (nombre, apellido, pais, edad, nombre_deporte, especialidad_deporte, tipo_deporte, mejor_marca_personal, es_extranjero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, atleta.getNombre());
            ps.setString(2, atleta.getApellido());
            ps.setString(3, atleta.getPais());
            ps.setInt(4, atleta.getEdad());
            
            // Usamos nombre_deporte de tu schema SQL
            ps.setString(5, atleta.getDeporte().getNombre()); 
            ps.setString(6, atleta.getDeporte().getEspecialidad());
            ps.setString(7, atleta.getDeporte().getTipo());
            
            ps.setDouble(8, atleta.getMejorMarcaPersonal());
            // Se mantiene la referencia a esExtranjero (Asumiendo que existe en la DB)
            ps.setBoolean(9, atleta.isEsExtranjero()); 
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        atleta.setId(rs.getInt(1));
                    }
                }
                System.out.println("DAO: Atleta " + atleta.getNombre() + " guardado con ID: " + atleta.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar atleta: " + e.getMessage());
            throw e;
        }
    }

    /** Implementación: OBTENER TODOS (SELECT) los atletas */
    public List<Atleta> obtenerTodos() throws SQLException {
        List<Atleta> atletas = new ArrayList<>();
        // CORRECCIÓN SQL: Usamos nombre_deporte, apellido, mejor_marca_personal, es_extranjero
        String sql = "SELECT id, nombre, apellido, pais, edad, nombre_deporte, especialidad_deporte, tipo_deporte, mejor_marca_personal, es_extranjero FROM atletas";
        
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Atleta atleta = new Atleta();
                atleta.setId(rs.getInt("id"));
                atleta.setNombre(rs.getString("nombre"));
                atleta.setApellido(rs.getString("apellido"));
                atleta.setPais(rs.getString("pais"));
                atleta.setEdad(rs.getInt("edad"));
                atleta.setMejorMarcaPersonal(rs.getDouble("mejor_marca_personal"));
                // Corrección de símbolo: Usamos el método setEsExtranjero()
                atleta.setEsExtranjero(rs.getBoolean("es_extranjero")); 
                
                // Mapear el Deporte usando los 3 campos de la DB
                String nombreDeporte = rs.getString("nombre_deporte");
                String especialidadDeporte = rs.getString("especialidad_deporte");
                String tipoDeporte = rs.getString("tipo_deporte");
                atleta.setDeporte(new Deporte(nombreDeporte, especialidadDeporte, tipoDeporte));
                
                atletas.add(atleta);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener atletas: " + e.getMessage());
            throw e;
        }
        System.out.println("DAO: Se obtuvieron " + atletas.size() + " atletas de la DB.");
        return atletas;
    }

    /** Implementación: OBTENER POR ID (SELECT WHERE) */
    public Atleta obtenerPorId(int id) throws SQLException {
        // CORRECCIÓN SQL: Usamos nombre_deporte, apellido, mejor_marca_personal, es_extranjero
        String sql = "SELECT id, nombre, apellido, pais, edad, nombre_deporte, especialidad_deporte, tipo_deporte, mejor_marca_personal, es_extranjero FROM atletas WHERE id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Atleta atleta = new Atleta();
                    atleta.setId(rs.getInt("id"));
                    atleta.setNombre(rs.getString("nombre"));
                    atleta.setApellido(rs.getString("apellido"));
                    atleta.setPais(rs.getString("pais"));
                    atleta.setEdad(rs.getInt("edad"));
                    atleta.setMejorMarcaPersonal(rs.getDouble("mejor_marca_personal"));
                    // Corrección de símbolo: Usamos el método setEsExtranjero()
                    atleta.setEsExtranjero(rs.getBoolean("es_extranjero"));

                    // Mapear el Deporte usando los 3 campos de la DB
                    String nombreDeporte = rs.getString("nombre_deporte");
                    String especialidadDeporte = rs.getString("especialidad_deporte");
                    String tipoDeporte = rs.getString("tipo_deporte");
                    atleta.setDeporte(new Deporte(nombreDeporte, especialidadDeporte, tipoDeporte));
                    
                    System.out.println("DAO: Atleta ID " + id + " encontrado.");
                    return atleta;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener atleta por ID: " + e.getMessage());
            throw e;
        }
        return null;
    }
    
    // --- MÉTODOS PENDIENTES DEL CRUD COMPLETO PARA LA API ---
    
    /** Implementación pendiente: Actualizar un atleta (UPDATE) */
    public void actualizar(Atleta atleta) throws SQLException {
        // Implementación requerida para la API
        System.out.println("DAO: Método actualizar aún no implementado.");
        // Ejemplo: String sql = "UPDATE atletas SET nombre=?, pais=?, ... WHERE id=?";
    }
    
    /** Implementación pendiente: Eliminar un atleta (DELETE) */
    public void eliminar(int id) throws SQLException {
        // Implementación requerida para la API
        System.out.println("DAO: Método eliminar aún no implementado.");
        // Ejemplo: String sql = "DELETE FROM atletas WHERE id=?";
    }
}