package com.life.demo.mapper;

import com.life.demo.model.QuestionModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into QUESTION(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtcreate},#{gmtmodified},#{creator},#{tag})" )
    public void create(QuestionModel questionModel);

    @Select("select * from QUESTION limit #{offset},#{size}")
    List<QuestionModel> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from QUESTION")
    Integer count();

    @Select("select * from QUESTION where creator=#{userid} limit #{offset},#{size}")
    List<QuestionModel> listProfile(@Param(value = "userid") Integer userid, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from QUESTION where creator=#{userid}")
    Integer counts(@Param(value = "userid") Integer userid);
}
