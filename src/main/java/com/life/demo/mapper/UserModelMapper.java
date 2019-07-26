package com.life.demo.mapper;

import com.life.demo.model.UserModel;
import com.life.demo.model.UserModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    long countByExample(UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    int deleteByExample(UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    int insert(UserModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    int insertSelective(UserModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    List<UserModel> selectByExampleWithRowbounds(UserModelExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    List<UserModel> selectByExample(UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    UserModel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    int updateByExampleSelective(@Param("record") UserModel record, @Param("example") UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    int updateByExample(@Param("record") UserModel record, @Param("example") UserModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    int updateByPrimaryKeySelective(UserModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USER
     *
     * @mbg.generated Sun Jul 14 16:23:23 CST 2019
     */
    int updateByPrimaryKey(UserModel record);
}