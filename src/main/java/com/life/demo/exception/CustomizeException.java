package com.life.demo.exception;

public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(CustomizeError customizeError) {
        this.message = customizeError.getMessage();
    }
    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
