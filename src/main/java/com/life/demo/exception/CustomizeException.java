package com.life.demo.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(CustomizeError customizeError) {
        this.code = customizeError.getCode();
        this.message = customizeError.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {//8.
        return code;
    }
}


