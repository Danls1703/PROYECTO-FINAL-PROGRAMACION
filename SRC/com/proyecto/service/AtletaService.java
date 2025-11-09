package com.proyecto.service;

import com.proyecto.model.Atleta;
import com.proyecto.model.Deporte;
import com.proyecto.negocio.GestorFinanciero;
import com.proyecto.repository.AtletaDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AtletaService {
    
    private final AtletaDAO atletaDAO;
    private final GestorFinanciero gestorFinanciero;

    public AtletaService(AtletaDAO atletaDAO) {
        this.atletaDAO = atletaDAO;
        this.gestorFinanciero = new GestorFinanciero();
    }

    public void registrarAtletaYEntrenamiento(Scanner scanner) throws SQLException {
        System.out.println("\n--- Registro de Atleta y Entrenamiento ---");
        
        // 1. Capturar datos del Atleta
        System.out.print("Nombre del Atleta: ");
        String nombre = scanner.nextLine();

        String apellido = "N/A";

        System.out.print("País: ");
        String pais = scanner.nextLine();

        int edad = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("Edad: ");
            if (scanner.hasNextInt()) {
                edad = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                entradaValida = true;
            } else {
                System.out.println("❌ Entrada inválida. Por favor, ingrese un número para la edad.");
                scanner.nextLine(); // Limpiar la entrada no válida
            }
        }
        
        System.out.print("Deporte principal (ej: Natación, Atletismo): ");
        String nombreDeporte = scanner.nextLine();
        
        System.out.print("¿Es extranjero? (true/false): ");
        boolean esExtranjero = Boolean.parseBoolean(scanner.nextLine());

        Deporte deporte = new Deporte(nombreDeporte, null, null); 
        
        Atleta nuevoAtleta = new Atleta();
        nuevoAtleta.setNombre(nombre);
        nuevoAtleta.setApellido(apellido); // Asignación temporal
        nuevoAtleta.setPais(pais);
        nuevoAtleta.setEdad(edad);
        nuevoAtleta.setDeporte(deporte);
        nuevoAtleta.setEsExtranjero(esExtranjero);
        
        // 3. Persistir el objeto usando el DAO
        try {
            atletaDAO.guardar(nuevoAtleta);
            System.out.println("✅ Atleta " + nombre + " registrado exitosamente en la base de datos.");
            System.out.println("Entrenamiento asociado al atleta (lógica del entrenamiento pendiente).");
        } catch (SQLException e) {
            System.err.println("❌ ERROR: No se pudo registrar el atleta debido a un error de base de datos.");
            throw e; 
        }
    }

    public Map<Deporte, Long> obtenerEstadisticasPorDeporte() throws SQLException {
        List<Atleta> todos = atletaDAO.obtenerTodos(); 
        return todos.stream()
                .collect(Collectors.groupingBy(Atleta::getDeporte, Collectors.counting()));
    }

    public void procesarPago(int atletaId) throws SQLException {
        Atleta atletaSeleccionado = atletaDAO.obtenerPorId(atletaId); 

        if (atletaSeleccionado != null) {
            double pagoTotal = gestorFinanciero.calcularPagoMensual(atletaSeleccionado);
            System.out.printf("✅ Pago mensual calculado para Atleta ID %d (%s): $%.2f%n", 
                               atletaId, atletaSeleccionado.getNombre(), pagoTotal);
        } else {
            System.out.println("❌ Atleta no encontrado con ID: " + atletaId);
        }
    }

    public boolean generarReporteCSV(String filename) throws SQLException, IOException {
        System.out.println("Generando reporte CSV...");
        return true; 
    }

    public List<Atleta> obtenerTodosAtletas() throws SQLException {
        return atletaDAO.obtenerTodos();
    }
    
    public Atleta obtenerPorId(int id) throws SQLException {
        return atletaDAO.obtenerPorId(id);
    }
    
    public Atleta registrarAtletaApi(Atleta nuevoAtleta) throws SQLException {
        atletaDAO.guardar(nuevoAtleta);
        return nuevoAtleta;
    }
    
    
    public void actualizarAtleta(Atleta atleta) throws SQLException {
        
        atletaDAO.actualizar(atleta);
    }

    public void eliminarAtleta(int id) throws SQLException {
        atletaDAO.eliminar(id);
    }
}