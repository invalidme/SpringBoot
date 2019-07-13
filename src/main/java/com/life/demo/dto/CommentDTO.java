package com.life.demo.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long parentid;
    private String content;
    private Integer type;
}
