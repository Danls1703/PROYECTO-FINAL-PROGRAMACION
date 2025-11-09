package com.proyecto.api;

public class StandardResponse {
    
    private StatusResponse status;
    private String message;
    private Object data;

    public StandardResponse(StatusResponse status) {
        this.status = status;
    }

    public StandardResponse(StatusResponse status, String message) {
        this.status = status;
        this.message = message;
    }

    public StandardResponse(StatusResponse status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    // Getters y Setters (Opcionales, pero buena práctica si los necesitas)

    public StatusResponse getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}