package com.examen.jacome.exception;

public class CreateEntityException extends RuntimeException {

    private final Integer errorCode;

    public CreateEntityException(String entityName, String message) {
        super(entityName + ": " + message);
        this.errorCode = 400; // Código de error para creación fallida (Bad Request)
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}

