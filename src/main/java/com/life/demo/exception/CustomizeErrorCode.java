package com.life.demo.exception;

public enum CustomizeErrorCode implements CustomizeError {
    QUESTION_NOT_FOUND(2001, "问题不存在wdnmd"),//5.
    TARGET_NOT_FOUND(2002, "未选择问题wdnmd"),//9.
    NO_LOGIN(2003, "你还没有未登录~~~"),
    SYS_ERROR(2004, "服务器炸了~~~"),
    TYPE_PARAM_WRONG(2005, "评论类型错误wdnmd"),
    COMMENT_NOT_FOUND(2006, "评论不存在wdnmd"),
    CONTENT_IS_EMPTY(6666,"回复内容不能为空~~~"),
    READ_NOTIFICATION_FAIL(8888,"你可以读别人的信息？？？"),
    NOTIFICATION_NOT_FOUND(9999,"通知不见了。。。"),
    MODIFY_QUESTION(9998,"请不要修改他人问题");
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
