package com.life.demo.mapper;

import com.life.demo.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//访问数据库中的内容
@Mapper
public interface UserMapper {
    @Insert("insert into USER(name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    //UserModel userModel是类的时候才能自动放
    void insert(UserModel userModel);


    @Select("select * from USER where token = #{token}")
    UserModel findByToken(@Param("token") String token);

}
