package com.life.demo.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),//3
    REPLY_COMMENT(2,"回复了评论");


    private int type;//1
    private String name;

    public int getType() {//4
        return type;
    }
    public String getName() {
        return name;
    }

     NotificationTypeEnum(int type, String name) {//2
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type){
        for(NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()){
            if(notificationTypeEnum.getType() == type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
