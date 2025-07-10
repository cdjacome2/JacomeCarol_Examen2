package com.examen.jacome.exception;

public class ResourceNotFoundException extends RuntimeException {

    private final Integer errorCode;

    public ResourceNotFoundException(String message) {
        super(message);
        this.errorCode = 404; // Código de error para recurso no encontrado (Not Found)
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
