package com.life.demo.Service;

import com.life.demo.dto.PageDTO;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
import com.life.demo.mapper.QuestionExtModelMapper;
import com.life.demo.mapper.QuestionModelMapper;
import com.life.demo.mapper.UserModelMapper;
import com.life.demo.model.QuestionModel;
import com.life.demo.model.QuestionModelExample;
import com.life.demo.model.UserModel;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionExtModelMapper questionExtModelMapper;

    @Autowired
    private UserModelMapper userMapper;

    @Autowired
    private QuestionModelMapper questionMapper;


    //首页分页
    public PageDTO list(Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();

        Integer allcount = (int) questionMapper.countByExample(new QuestionModelExample());

        //Integer allcount = questionMapper.count();
        pageDTO.setPageDTO(allcount, page, size);//当前页面
        if (page < 1) {
            page = 1;
        }
        if (page > pageDTO.getAllPage()) {
            page = pageDTO.getAllPage();
        }

        //page = size*(page-1)  5*(i-1)
        Integer offset = size * (page - 1);

        QuestionModelExample example = new QuestionModelExample();

        List<QuestionModel> questionModels = questionMapper.selectByExampleWithRowbounds(new QuestionModelExample(), new RowBounds(offset, size));

        //List<QuestionModel> questionModels = questionMapper.list(offset,size);//1.通过questionMapper.list（）查到所有的questionModel对象 //每一页的列表
        List<QuestionDTO> questionDTOList = new ArrayList<>();//6.因为返回的是一个DTO对象

        for (QuestionModel questionModel : questionModels) {

            UserModel userModel = userMapper.selectByPrimaryKey(questionModel.getCreator());

            // UserModel userModel= userMapper.findById(questionModel.getCreator());//2.每个对象都通过userMapper方法拿到Creator，返回userModel对象
            QuestionDTO questionDTO = new QuestionDTO();//3.把questionModel转换为DTO
            BeanUtils.copyProperties(questionModel, questionDTO);//4.把questionModel对象放入questionDTO中
            questionDTO.setUserModel(userModel);//5.

            questionDTOList.add(questionDTO);//7.每次创建新的questionDTO就把它add到questionDTOlist
        }//循环questionModel对象

        pageDTO.setQuestions(questionDTOList);


        return pageDTO;
    }

    //个人页分页
    public PageDTO listProfile(Long userid, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();

        QuestionModelExample example = new QuestionModelExample();
        example.createCriteria().andCreatorEqualTo(userid);
        Integer allcount = (int) questionMapper.countByExample(example);//往example里传一个userid

        //Integer allcount = questionMapper.counts(userid);
        pageDTO.setPageDTO(allcount, page, size);//当前页面
        if (page < 1) {
            page = 1;
        }
        if (page > pageDTO.getAllPage()) {
            page = pageDTO.getAllPage();
        }

        //page = size*(page-1)  5*(i-1)
        Integer offset = size * (page - 1);

        QuestionModelExample example1 = new QuestionModelExample();//把查询条件封装到这里
        example1.createCriteria().andCreatorEqualTo(userid);
        List<QuestionModel> questionModels = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));

        //List<QuestionModel> questionModels = questionMapper.listProfile(userid,offset,size);//1.通过questionMapper.list（）查到所有的questionModel对象 //每一页的列表
        List<QuestionDTO> questionDTOList = new ArrayList<>();//6.因为返回的是一个DTO对象

        for (QuestionModel questionModel : questionModels) {
            UserModel userModel = userMapper.selectByPrimaryKey(questionModel.getCreator());//2.每个对象都通过userMapper方法拿到Creator，返回userModel对象
            QuestionDTO questionDTO = new QuestionDTO();//3.把questionModel转换为DTO
            BeanUtils.copyProperties(questionModel, questionDTO);//4.把questionModel对象放入questionDTO中
            questionDTO.setUserModel(userModel);//5.

            questionDTOList.add(questionDTO);//7.每次创建新的questionDTO就把它add到questionDTOlist
        }//循环questionModel对象

        pageDTO.setQuestions(questionDTOList);

        return pageDTO;
    }

    //进入我的问题或首页问题
    public QuestionDTO getByID(Long id) {

        QuestionModel questionModel = questionMapper.selectByPrimaryKey(id);
        if(questionModel == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(questionModel, questionDTO);
        UserModel userModel = userMapper.selectByPrimaryKey(questionModel.getCreator());
        questionDTO.setUserModel(userModel);
        return questionDTO;
    }

    public void createUpdate(QuestionModel questionModel) {
        if (questionModel.getId() == null) {

            questionModel.setGmtModified(questionModel.getGmtCreate());
            questionModel.setViewCount(0);
            questionModel.setLikeCount(0);
            questionModel.setCommentCount(0);
            questionMapper.insert(questionModel);

        } else {

            QuestionModel updateQuestion = new QuestionModel();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(questionModel.getTitle());
            updateQuestion.setDescription(questionModel.getDescription());
            updateQuestion.setTag(questionModel.getTag());

            QuestionModelExample example = new QuestionModelExample();
            example.createCriteria().andIdEqualTo(questionModel.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void view(Long id) {//调用方法，传入id
        /*
        QuestionModel questionModel = questionMapper.selectByPrimaryKey(id);//4.拿到数据库里的question
        QuestionModel updateQuestionModel = new QuestionModel();//2
        updateQuestionModel.setViewCount(questionModel.getViewCount() + 1);//3  5

        QuestionModelExample example = new QuestionModelExample();
        example.createCriteria().andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestionModel, example);//1
        */
        QuestionModel questionModel = new QuestionModel();
        questionModel.setId(id);
        questionModel.setViewCount(1);
        questionExtModelMapper.view(questionModel);
    }
}
