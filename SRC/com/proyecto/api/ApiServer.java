package com.proyecto.api; 

import spark.Spark;
import com.fasterxml.jackson.databind.ObjectMapper; // Usaremos Jackson (si tienes los JARs en /lib)
import com.proyecto.service.AtletaService; 
import com.proyecto.model.Atleta; 
import java.util.HashMap;
import java.util.Map;

public class ApiServer {

    private final AtletaService atletaService;
    // Utilizamos Jackson para la serialización
    private final ObjectMapper mapper = new ObjectMapper(); 

    public ApiServer(AtletaService service) {
        this.atletaService = service;
        Spark.port(4567);
        // Filtro para manejo de excepciones
        Spark.exception(Exception.class, (e, req, res) -> {
            res.status(500);
            res.type("application/json");
            res.body(jsonResponse(500, "error", "Error interno del servidor: " + e.getMessage()));
        });
        
        configurarRutas();
    }
    
    // Método estático para iniciar el servidor (llamado desde Main)
    public static void iniciar(AtletaService atletaService) {
        new ApiServer(atletaService);
        System.out.println("API REST iniciada en http://localhost:4567. Accede a /api/v1/atletas");
    }

    private void configurarRutas() {
        
        // --- FILTRO GLOBAL: Establece el tipo de contenido para todas las respuestas ---
        Spark.before((req, res) -> res.type("application/json"));

        // 1. GET /api/v1/atletas (Listar todos)
        Spark.get("/api/v1/atletas", (req, res) -> {
            Object data = atletaService.obtenerTodosAtletas();
            return jsonResponse(200, "success", data);
        });

        // 2. GET /api/v1/atletas/:id (Obtener por ID)
        Spark.get("/api/v1/atletas/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Atleta atleta = atletaService.obtenerPorId(id);
            
            if (atleta != null) {
                return jsonResponse(200, "success", atleta);
            }
            res.status(404);
            return jsonResponse(404, "error", "Atleta no encontrado con ID: " + id);
        });

        // 3. POST /api/v1/atletas (Crear nuevo)
        Spark.post("/api/v1/atletas", (req, res) -> {
            try {
                // Deserializar el JSON del cuerpo de la solicitud a un objeto Atleta
                Atleta nuevoAtleta = mapper.readValue(req.body(), Atleta.class);
                // Usamos el método que llama a DAO.guardar()
                Atleta atletaCreado = atletaService.registrarAtletaApi(nuevoAtleta); 
                res.status(201); // Código HTTP 201 Created
                return jsonResponse(201, "success", atletaCreado);
            } catch (Exception e) {
                res.status(400); // 400 Bad Request si el JSON es inválido o faltan datos
                return jsonResponse(400, "error", "Error procesando la solicitud: " + e.getMessage());
            }
        });
        
        // 4. PUT /api/v1/atletas/:id (Actualizar existente)
        Spark.put("/api/v1/atletas/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Atleta atletaActualizado = mapper.readValue(req.body(), Atleta.class);
            atletaActualizado.setId(id); // Asegurar que el ID del objeto coincida con el de la ruta
            
            // Llama al servicio para ejecutar la lógica de actualización
            atletaService.actualizarAtleta(atletaActualizado); 
            
            return jsonResponse(200, "success", "Atleta ID " + id + " actualizado con éxito.");
        });

        // 5. DELETE /api/v1/atletas/:id (Eliminar)
        Spark.delete("/api/v1/atletas/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            
            atletaService.eliminarAtleta(id);
            
            return jsonResponse(200, "success", "Atleta ID " + id + " eliminado con éxito.");
        });
    }
    
    /**
     * Crea una respuesta JSON estandarizada con status/data/code usando Jackson.
     */
    private String jsonResponse(int codigo_http, String status, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("code", codigo_http);
        response.put("data", data);

        try {
            return mapper.writeValueAsString(response);
        } catch (Exception e) {
            // Este catch maneja fallas en la propia serialización
            e.printStackTrace();
            return "{\"status\":\"error\", \"code\":500, \"data\":\"Error serializando JSON\"}";
        }
    }
}