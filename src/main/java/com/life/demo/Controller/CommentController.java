package com.life.demo.Controller;

import com.life.demo.Service.CommentService;
import com.life.demo.dto.CommentDTO;
import com.life.demo.dto.ResultDTO;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.mapper.CommentModelMapper;
import com.life.demo.model.CommentModel;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentModelMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @ResponseBody//把对象自动序列化为json，发到前端
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,//接收json格式的数据
                       HttpServletRequest request) {

        UserModel  userModel =(UserModel) request.getSession().getAttribute("userModel");
        if(userModel == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        CommentModel commentModel = new CommentModel();
        commentModel.setParentId(commentDTO.getParentid());
        commentModel.setType(commentDTO.getType());
        commentModel.setContent(commentDTO.getContent());
        commentModel.setGmtModified(commentModel.getGmtModified());
        commentModel.setGmtCreate(System.currentTimeMillis());
        commentModel.setCommentator(userModel.getId());
        commentModel.setLikeCount(0l);
        commentService.insert(commentModel);
        return ResultDTO.okOf();
    }
}