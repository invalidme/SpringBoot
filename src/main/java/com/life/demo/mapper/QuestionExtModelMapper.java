package com.life.demo.mapper;

import com.life.demo.model.QuestionModel;
import com.life.demo.model.QuestionModelExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtModelMapper {//自动映射到QuestionExtMapper.xml
        int view(QuestionModel record);
        int CommentCount(QuestionModel record);
        List<QuestionModel> selectRelated(QuestionModel questionModel);//->QuestionController
}