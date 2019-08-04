package com.life.demo.Controller;

import com.life.demo.Service.CommentService;
import com.life.demo.dto.CommentCreateDTO;
import com.life.demo.dto.ResultDTO;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.model.CommentModel;
import com.life.demo.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody//把对象自动序列化为json，发到前端
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,//接收json格式的数据
                       HttpServletRequest request) {

        UserModel  userModel =(UserModel) request.getSession().getAttribute("userModel");
        if(userModel == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        CommentModel commentModel = new CommentModel();
        commentModel.setParentId(commentCreateDTO.getParentId());
        commentModel.setType(commentCreateDTO.getType());
        commentModel.setContent(commentCreateDTO.getContent());
        commentModel.setGmtModified(System.currentTimeMillis());
        commentModel.setGmtCreate(System.currentTimeMillis());
        commentModel.setCommentator(userModel.getId());
        commentModel.setLikeCount(0l);
        commentService.insert(commentModel);
        return ResultDTO.okOf();
    }
}
