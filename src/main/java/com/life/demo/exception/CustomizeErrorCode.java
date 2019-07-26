package com.life.demo.exception;

public enum CustomizeErrorCode implements CustomizeError {
    QUESTION_NOT_FOUND(2001, "问题不存在wdnmd"),//5.
    TARGET_NOT_FOUND(2002, "未选择问题wdnmd"),//9.
    NO_LOGIN(2003, "未登录wdnmd"),
    SYS_ERROR(2004, "服务器炸了wdnmd"),
    TYPE_PARAM_WRONG(2005, "评论类型错误wdnmd"),
    COMMENT_NOT_FOUND(2006, "评论不存在wdnmd");
    private String message;
    private Integer code;//2.

    CustomizeErrorCode(Integer code, String message) {//3.
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {//4.
        return code;
    }
}
