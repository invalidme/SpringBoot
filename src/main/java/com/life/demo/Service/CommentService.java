package com.life.demo.Service;

import com.life.demo.enums.CommentTypeEnum;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
import com.life.demo.mapper.CommentModelMapper;
import com.life.demo.mapper.QuestionExtModelMapper;
import com.life.demo.mapper.QuestionModelMapper;
import com.life.demo.model.CommentModel;
import com.life.demo.model.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentModelMapper commentModelMapper;
    @Autowired
    private QuestionModelMapper questionModelMapper;
    @Autowired
    private QuestionExtModelMapper questionExtModelMapper;

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
            } else {
                commentModelMapper.insert(commentModel);
            }
        } else {
            //回复问题
            QuestionModel questionModel = questionModelMapper.selectByPrimaryKey(commentModel.getParentId());
            if(questionModel == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentModelMapper.insert(commentModel);
            questionModel.setCommentCount(1);
            questionExtModelMapper.CommentCount(questionModel);

        }
    }
}
