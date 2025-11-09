package com.proyecto.ui;
import com.proyecto.service.AtletaService;
import java.io.IOException; 
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner; 

public class ConsolaUI {
    
    private final AtletaService atletaService;
    private final Scanner scanner;

    public ConsolaUI(AtletaService atletaService) {
        this.atletaService = atletaService;
        this.scanner = new Scanner(System.in); 
    }
    
    public void ejecutarMenuPrincipal() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenuPrincipal();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); 
                procesarOpcion(opcion); 
            } catch (java.util.InputMismatchException e) {
                System.out.println("❌ Entrada no válida. Por favor, ingrese un número.");
                scanner.nextLine(); 
                opcion = -1;
            } catch (SQLException e) { 
                System.err.println("❌ ERROR de Base de Datos: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("❌ ERROR de Archivo: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ Error inesperado: " + e.getMessage());
            }
        }
        scanner.close(); 
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n==============================================");
        System.out.println("         📊 Sistema de Gestión de Atletas 🏆     ");
        System.out.println("==============================================");
        System.out.println("1. 🧑‍💻  Registrar Atleta y Entrenamiento"); 
        System.out.println("2. 📈  Consultar Estadísticas");
        System.out.println("3. 💰  Procesar Pago de Planilla (Gestión Financiera)");
        System.out.println("4. 💾  Gestión de Persistencia (Guardar/Cargar)");
        System.out.println("5. 📄  Generar Reporte CSV");
        System.out.println("0. 🚪  Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void procesarOpcion(int opcion) throws SQLException, IOException { 
        switch (opcion) {
            case 1:
                atletaService.registrarAtletaYEntrenamiento(scanner); 
                break;
            case 2:
                mostrarEstadisticas();
                break;
            case 3:
                procesarPagoPlanilla();
                break;
            case 4:
                mostrarMenuPersistencia();
                break;
            case 5:
                generarReporteCSV();
                break;
            case 0:
                System.out.println("Saliendo de la aplicación. ¡Hasta pronto!");
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
                break;
        }
    }

    // --- Métodos Auxiliares de Lógica de Consola ---

    private void mostrarEstadisticas() throws SQLException {
        System.out.println("\n--- Estadísticas por Deporte ---");
        // Utilizamos el tipo completo por si acaso el compilador lo necesita
        Map<com.proyecto.model.Deporte, Long> stats = atletaService.obtenerEstadisticasPorDeporte();
        
        if (stats == null || stats.isEmpty()) {
            System.out.println("No hay atletas registrados o la consulta falló.");
            return;
        }
        
        stats.forEach((deporte, count) -> 
            
            System.out.println("- " + deporte.getNombre() + ": " + count + " atletas")
        );
        System.out.println("--------------------------------");
    }

    private void procesarPagoPlanilla() throws SQLException {
        System.out.println("\n--- Procesar Pago de Planilla ---");
        System.out.print("Ingrese ID del Atleta para calcular el pago: ");
        
        if (scanner.hasNextInt()) {
            int atletaId = scanner.nextInt();
            scanner.nextLine();
            atletaService.procesarPago(atletaId); 
        } else {
            System.out.println("ID inválido. Volviendo al menú principal.");
            scanner.nextLine();
        }
    }

    private void generarReporteCSV() throws SQLException, IOException { 
        System.out.println("\n--- Generar Reporte CSV ---");
        
        if (atletaService.generarReporteCSV("reporte_atletas.csv")) {
            System.out.println("✅ Reporte generado exitosamente en el archivo reporte_atletas.csv");
        } else {
            System.out.println("❌ El reporte CSV no se pudo generar. Verifique la lógica del servicio.");
        }
    }

    private void mostrarMenuPersistencia() throws SQLException {
        System.out.println("\n--- Gestión de Persistencia (Funcionalidad Simulada) ---");
        System.out.println("1. 💾 Guardar datos (Persistencia)");
        System.out.println("2. 📤 Cargar datos (Persistencia)");
        System.out.print("Seleccione una opción: ");

        if (scanner.hasNextInt()) {
            int subOpcion = scanner.nextInt();
            scanner.nextLine();
            if (subOpcion == 1 || subOpcion == 2) {
                System.out.println("Función de persistencia llamada. (Implementación del servicio pendiente).");
            } else {
                System.out.println("Opción de persistencia no válida.");
            }
        } else {
            System.out.println("Entrada inválida. Volviendo al menú principal.");
            scanner.nextLine();
        }
    }
}