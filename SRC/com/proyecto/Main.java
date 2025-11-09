package com.proyecto;

import com.proyecto.api.ApiServer;
import com.proyecto.repository.AtletaDAO;
import com.proyecto.service.AtletaService;
import com.proyecto.ui.ConsolaUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    
    // CONFIGURACIÓN DE LA BASE DE DATOS (AJUSTAR SEGÚN TU ENTORNO)
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/atletas_db?serverTimezone=UTC"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "root";

    public static void main(String[] args) {
        
        Connection connection = null;
        try {
            // 1. Cargar Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            
            // 2. Establecer Conexión
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ Conexión a la base de datos exitosa.");

            // 3. Inicializar DAO/Service pasando la conexión
            // Se asume que AtletaDAO requiere Connection
            AtletaDAO atletaDAO = new AtletaDAO(connection);
            AtletaService atletaService = new AtletaService(atletaDAO);

            if (args.length > 0 && "api".equalsIgnoreCase(args[0])) {
                // MODO API REST
                System.out.println("\n🌐 Iniciando Servidor API REST (Puerto 4567)");
                ApiServer.iniciar(atletaService); 
                
            } else {
                // MODO CONSOLA
                System.out.println("\n💻 Iniciando Interfaz de Consola...");
                ConsolaUI consola = new ConsolaUI(atletaService);
                consola.ejecutarMenuPrincipal(); 
            }

        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR FATAL: No se encontró el driver JDBC de MySQL.");
        } catch (SQLException e) { 
            System.err.println("❌ ERROR FATAL: No se pudo conectar a la base de datos.");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ ERROR INESPERADO al iniciar la aplicación.");
            e.printStackTrace();
        } finally {
             boolean isApiMode = args.length > 0 && "api".equalsIgnoreCase(args[0]);
             if (connection != null && !isApiMode) {
                try {
                    connection.close(); 
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
             }
        }
    }
}