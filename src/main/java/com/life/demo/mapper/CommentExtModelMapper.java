package com.life.demo.mapper;

import com.life.demo.model.CommentModel;
import com.life.demo.model.CommentModelExample;
import com.life.demo.model.QuestionModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtModelMapper {
    int CommentCount(CommentModel commentModel);
}