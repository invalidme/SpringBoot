package com.life.demo.dto;

import lombok.Data;
import org.apache.catalina.User;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer type;
    private Long outerid;
}
