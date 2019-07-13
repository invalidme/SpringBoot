package com.life.demo.enums;

public enum CommentTypeEnum {
    QUESTION(1),

    COMMENT(2);

    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {//构造方法
        this.type = type;
    }
}
