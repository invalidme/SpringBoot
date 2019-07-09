package com.life.demo.Service;

import com.life.demo.dto.PageDTO;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.mapper.QuestionMapper;
import com.life.demo.mapper.UserMapper;
import com.life.demo.model.QuestionModel;
import com.life.demo.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;


    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer allcount = questionMapper.count();
        pageDTO.setPageDTO(allcount,page,size);//当前页面
        if(page < 1){
            page = 1;
        }
        if(page > pageDTO.getAllPage()){
            page = pageDTO.getAllPage();
        }

         //page = size*(page-1)  5*(i-1)
        Integer offset = size*(page-1);

        List<QuestionModel> questionModels = questionMapper.list(offset,size);//1.通过questionMapper.list（）查到所有的questionModel对象 //每一页的列表
        List<QuestionDTO> questionDTOList=new ArrayList<>();//6.因为返回的是一个DTO对象

        for(QuestionModel questionModel : questionModels){
            UserModel userModel= userMapper.findById(questionModel.getCreator());//2.每个对象都通过userMapper方法拿到Creator，返回userModel对象
            QuestionDTO questionDTO = new QuestionDTO();//3.把questionModel转换为DTO
            BeanUtils.copyProperties(questionModel,questionDTO);//4.把questionModel对象放入questionDTO中
            questionDTO.setUserModel(userModel);//5.

            questionDTOList.add(questionDTO);//7.每次创建新的questionDTO就把它add到questionDTOlist
        }//循环questionModel对象

        pageDTO.setQuestions(questionDTOList);


        return  pageDTO;
    }

    public PageDTO listProfile(Integer userid, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer allcount = questionMapper.counts(userid);
        pageDTO.setPageDTO(allcount,page,size);//当前页面
        if(page < 1){
            page = 1;
        }
        if(page > pageDTO.getAllPage()){
            page = pageDTO.getAllPage();
        }

        //page = size*(page-1)  5*(i-1)
        Integer offset = size*(page-1);

        List<QuestionModel> questionModels = questionMapper.listProfile(userid,offset,size);//1.通过questionMapper.list（）查到所有的questionModel对象 //每一页的列表
        List<QuestionDTO> questionDTOList=new ArrayList<>();//6.因为返回的是一个DTO对象

        for(QuestionModel questionModel : questionModels){
            UserModel userModel= userMapper.findById(questionModel.getCreator());//2.每个对象都通过userMapper方法拿到Creator，返回userModel对象
            QuestionDTO questionDTO = new QuestionDTO();//3.把questionModel转换为DTO
            BeanUtils.copyProperties(questionModel,questionDTO);//4.把questionModel对象放入questionDTO中
            questionDTO.setUserModel(userModel);//5.

            questionDTOList.add(questionDTO);//7.每次创建新的questionDTO就把它add到questionDTOlist
        }//循环questionModel对象

        pageDTO.setQuestions(questionDTOList);


        return pageDTO;
    }
}
