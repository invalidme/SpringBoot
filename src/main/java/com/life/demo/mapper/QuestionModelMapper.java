package com.life.demo.mapper;

import com.life.demo.model.QuestionModel;
import com.life.demo.model.QuestionModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QuestionModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    long countByExample(QuestionModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int deleteByExample(QuestionModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int insert(QuestionModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int insertSelective(QuestionModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    List<QuestionModel> selectByExampleWithBLOBsWithRowbounds(QuestionModelExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    List<QuestionModel> selectByExampleWithBLOBs(QuestionModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    List<QuestionModel> selectByExampleWithRowbounds(QuestionModelExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    List<QuestionModel> selectByExample(QuestionModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    QuestionModel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int updateByExampleSelective(@Param("record") QuestionModel record, @Param("example") QuestionModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int updateByExampleWithBLOBs(@Param("record") QuestionModel record, @Param("example") QuestionModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int updateByExample(@Param("record") QuestionModel record, @Param("example") QuestionModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int updateByPrimaryKeySelective(QuestionModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int updateByPrimaryKeyWithBLOBs(QuestionModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Thu Feb 06 13:36:03 CST 2020
     */
    int updateByPrimaryKey(QuestionModel record);
}