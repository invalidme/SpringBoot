package com.life.demo.dto;

import com.life.demo.model.Register;
import com.life.demo.model.UserModel;
import lombok.Data;
import org.h2.engine.User;
@Data
public class QuestionDTO {
        private Long id;
        private String title;
        private String description;
        private String tag;
        private Long gmtCreate;
        private Long gmtModified;
        private Long creator;
        private Integer viewCount;
        private Integer likeCount;
        private Integer commentCount;

        private UserModel userModel;
        private Register register;
    }


