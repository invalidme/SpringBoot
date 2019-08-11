package com.life.demo.Service;

import com.life.demo.dto.CommentDTO;
import com.life.demo.enums.CommentTypeEnum;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
import com.life.demo.mapper.*;
import com.life.demo.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentModelMapper commentModelMapper;
    @Autowired
    private QuestionModelMapper questionModelMapper;
    @Autowired
    private QuestionExtModelMapper questionExtModelMapper;
    @Autowired
    private UserModelMapper userModelMapper;

    @Autowired
    private CommentExtModelMapper commentExtModelMapper;
    @Transactional//把整个方法体增加一个事务
    public void insert(CommentModel commentModel) {
        if (commentModel.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }
        if (commentModel.getType() == null || !CommentTypeEnum.isExist(commentModel.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (commentModel.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            CommentModel dbComment = commentModelMapper.selectByPrimaryKey(commentModel.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentModelMapper.insert(commentModel);
            //增加评论的评论数
            CommentModel parentComment = new CommentModel();
            parentComment.setId(commentModel.getParentId());
            parentComment.setCommentCount(1);
            commentExtModelMapper.CommentCount(parentComment);

        } else {
            //回复问题
            QuestionModel questionModel = questionModelMapper.selectByPrimaryKey(commentModel.getParentId());
            if(questionModel == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentModel.setCommentCount(0);
            commentModelMapper.insert(commentModel);
            questionModel.setCommentCount(1);
            questionExtModelMapper.CommentCount(questionModel);
        }
    }
    public List<CommentDTO> listByCommentId(Long id, CommentTypeEnum type){//抽参数后增加了type
        CommentModelExample commentModelExample = new CommentModelExample();
        commentModelExample.createCriteria().andParentIdEqualTo(id)
                                            .andTypeEqualTo(type.getType());//ctrl+alt+v抽参数,原CommentTypeEnum.QUESTION.getType()
        //按时间顺序排列 数据库语句
        commentModelExample.setOrderByClause("gmt_create desc");
        List<CommentModel> commentModels = commentModelMapper.selectByExample(commentModelExample);

        //java8语法
        if(commentModels.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = commentModels.stream().map(commentModel -> commentModel.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转换为 Map
        UserModelExample userModelExample = new UserModelExample();
        userModelExample.createCriteria().andIdIn(userIds);
        List<UserModel> userModels= userModelMapper.selectByExample(userModelExample);
        Map<Long,UserModel> userModelMap = userModels.stream().collect(Collectors.toMap(userModel->userModel.getId(), userModel -> userModel));

        //转换commentModel 为 commentDTO
        List<CommentDTO> commentDTOS = commentModels.stream().map(commentModel -> {
            CommentDTO commentDTO =new CommentDTO();
            BeanUtils.copyProperties(commentModel,commentDTO);
            commentDTO.setUserModel(userModelMap.get(commentModel.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
