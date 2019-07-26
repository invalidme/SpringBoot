package com.life.demo.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;//6.

    public CustomizeException(CustomizeError customizeError) {
        this.code = customizeError.getCode();//7.
        this.message = customizeError.getMessage();
    }
    /*public CustomizeException(String message) {
        this.message = message;
    }*/

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {//8.
        return code;
    }
}


