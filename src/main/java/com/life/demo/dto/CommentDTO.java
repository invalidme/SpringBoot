package com.life.demo.dto;

import com.life.demo.model.Register;
import com.life.demo.model.UserModel;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private UserModel userModel;
    private Register register;
    private Integer commentCount;
}
