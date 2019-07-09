package com.life.demo.dto;

import com.life.demo.model.UserModel;
import org.h2.engine.User;

public class QuestionDTO {
        private Integer id;
        private String title;
        private String description;
        private String tag;
        private Long gmtcreate;
        private Long gmtmodified;
        private Integer creator;
        private Integer viewcount;
        private Integer likecount;
        private Integer commentcount;

        private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public Long getGmtcreate() {
            return gmtcreate;
        }

        public void setGmtcreate(Long gmtcreate) {
            this.gmtcreate = gmtcreate;
        }

        public Long getGmtmodified() {
            return gmtmodified;
        }

        public void setGmtmodified(Long gmtmodified) {
            this.gmtmodified = gmtmodified;
        }

        public Integer getCreator() {
            return creator;
        }

        public void setCreator(Integer creator) {
            this.creator = creator;
        }

        public Integer getViewcount() {
            return viewcount;
        }

        public void setViewcount(Integer viewcount) {
            this.viewcount = viewcount;
        }

        public Integer getLikecount() {
            return likecount;
        }

        public void setLikecount(Integer likecount) {
            this.likecount = likecount;
        }

        public Integer getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(Integer commentcount) {
            this.commentcount = commentcount;
        }
    }


