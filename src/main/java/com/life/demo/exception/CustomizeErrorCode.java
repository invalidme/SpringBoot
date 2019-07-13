package com.life.demo.exception;

public enum CustomizeErrorCode implements CustomizeError {
    QUESTION_NOT_FOUND("问题不存在wdnmd");
    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
