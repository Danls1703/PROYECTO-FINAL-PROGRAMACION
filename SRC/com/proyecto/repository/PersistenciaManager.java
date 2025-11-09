package com.proyecto.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.*;
import java.util.List;
import java.util.ArrayList; 

import com.proyecto.model.Atleta; 
import com.proyecto.model.SesionEntrenamiento;
import com.proyecto.model.Marca;
import com.proyecto.model.Deporte;

/**
 * Clase encargada de manejar la persistencia de datos (guardar y cargar).
 * Utiliza Jackson para la serialización/deserialización de JSON.
 */
public class PersistenciaManager {
    private static final String JSON_PATH = "atletas_backup.json";
    private static final String CSV_PATH = "reporte_entrenamientos.csv";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PersistenciaManager() {
        // Configuración de Jackson para un JSON legible
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Desactiva la escritura de fechas como timestamps Unix
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); 
    }

    /**
     * Carga una lista de atletas a partir de un String JSON (usado por la API).
     * @param jsonBody El string JSON a deserializar.
     * @return El número de atletas procesados.
     */
    public int cargarAtletasDesdeJSONString(String jsonBody) throws Exception {
        
        // Deserializa el String JSON en una Lista de Atletas
        List<Atleta> atletasNuevos = objectMapper.readValue(jsonBody, new TypeReference<List<Atleta>>(){});
        
        // Retorna el número de registros (el AppManager debe manejar la adición real)
        return atletasNuevos.size(); 
    }

    /**
     * Guarda la lista de atletas en un archivo JSON.
     */
    public boolean guardarAtletasEnJSON(List<Atleta> atletas) {
        try {
            objectMapper.writeValue(new File(JSON_PATH), atletas);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carga la lista de atletas desde el archivo JSON de respaldo.
     */
    public List<Atleta> cargarAtletasDesdeJSON() {
        try {
            return objectMapper.readValue(new File(JSON_PATH), new TypeReference<List<Atleta>>() {});
        } catch (FileNotFoundException e) {
            System.out.println("Archivo JSON no encontrado. Se inicia con lista vacía.");
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Exporta un reporte detallado de todas las sesiones de entrenamiento a un archivo CSV.
     */
    public boolean exportarReporteCSV(List<Atleta> atletas) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_PATH))) {
            pw.println("Atleta;Pais;Deporte;SesionTipo;Fecha;MejorMarca;Unidad");

            for (Atleta atleta : atletas) {
                for (SesionEntrenamiento sesion : atleta.getSesiones()) {
                    Marca mejorMarca = sesion.mejorMarca();
                    
                    String linea = String.format("%s %s;%s;%s;%s;%s;%s;%s",
                            atleta.getNombre(), atleta.getApellido(), atleta.getPais(),
                            atleta.getDeporte().getNombre(), sesion.getTipo(),
                            sesion.getFecha(),
                            mejorMarca != null ? mejorMarca.getValor() : "N/A",
                            mejorMarca != null ? mejorMarca.getUnidad() : "N/A");
                    pw.println(linea);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}