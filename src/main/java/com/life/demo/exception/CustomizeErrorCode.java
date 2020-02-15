package com.life.demo.exception;

public enum CustomizeErrorCode implements CustomizeError {
    QUESTION_NOT_FOUND(2001, "问题不存在~~~"),
    TARGET_NOT_FOUND(2002, "问题消失了~~~"),
    NO_LOGIN(2003, "你还没有登录~~~"),
    SYS_ERROR(2004, "服务器炸了！！！。。。。。。。。。。。。。"),
    TYPE_PARAM_WRONG(2005, "评论类型错误~~~"),
    COMMENT_NOT_FOUND(2006, "你回复的评论不存在~~~"),
    CONTENT_IS_EMPTY(6666,"回复内容不能为空~~~"),
    READ_NOTIFICATION_FAIL(8888,"你可以读别人的信息~~~"),
    NOTIFICATION_NOT_FOUND(9999,"通知不见了~~~"),
    MODIFY_QUESTION(9998,"请不要修改他人问题~~~"),
    USERNAME_OR_PASSWORD_ERROR(10000,"用户名或密码错误"),
    ACCOUNT_IS_EMPTY(10001,"账号密码不能为空");
    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public Integer getCode() {
        return code;
    }
}
