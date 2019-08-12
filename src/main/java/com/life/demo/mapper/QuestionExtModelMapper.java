package com.life.demo.mapper;

import com.life.demo.dto.QuestionQueryDTO;
import com.life.demo.model.QuestionModel;

import java.util.List;

public interface QuestionExtModelMapper {//自动映射到QuestionExtMapper.xml
        int view(QuestionModel record);
        int CommentCount(QuestionModel record);
        List<QuestionModel> selectRelated(QuestionModel questionModel);//->QuestionController
        Integer countBySearch(QuestionQueryDTO questionQueryDTO);

        List<QuestionModel> selectBySearch(QuestionQueryDTO questionQueryDTO);
}