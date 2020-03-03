package com.life.demo.Service;

import com.life.demo.dto.PageDTO;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.dto.QuestionQueryDTO;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
import com.life.demo.mapper.QuestionExtModelMapper;
import com.life.demo.mapper.QuestionModelMapper;
import com.life.demo.mapper.RegisterMapper;
import com.life.demo.mapper.UserModelMapper;
import com.life.demo.model.QuestionModel;
import com.life.demo.model.QuestionModelExample;
import com.life.demo.model.Register;
import com.life.demo.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionExtModelMapper questionExtModelMapper;
    @Autowired
    private UserModelMapper userMapper;
    @Autowired
    private QuestionModelMapper questionMapper;
    @Autowired
    private RegisterMapper registerMapper;
    //首页分页
    public PageDTO list(String search, Integer page, Integer size) {
        if(StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search," ");

            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        PageDTO pageDTO = new PageDTO();
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtModelMapper.countBySearch(questionQueryDTO);
        pageDTO.setPageLogic(totalCount, page, size);//完成分页逻辑
        if (page < 1) {
            page = 1;
        }
        if (page > pageDTO.getTotalPage()) {
            page = pageDTO.getTotalPage();
        }

        Integer limit = size * (page - 1);
        questionQueryDTO.setPage(limit);
        questionQueryDTO.setSize(size);
        QuestionModelExample example = new QuestionModelExample();
        example.setOrderByClause("gmt_create desc");
        List<QuestionModel> questionList = questionExtModelMapper.selectBySearch(questionQueryDTO);//数据库搜索问题
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (QuestionModel questionModel : questionList) {
            UserModel userModel = userMapper.selectByPrimaryKey(questionModel.getCreator());
            Register register = registerMapper.selectByPrimaryKey(questionModel.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(questionModel, questionDTO);
            questionDTO.setUserModel(userModel);
            questionDTO.setRegister(register);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setData(questionDTOList);
        return pageDTO;
    }

    //个人页分页
    public PageDTO listProfile(Long userid, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();

        QuestionModelExample example = new QuestionModelExample();
        example.createCriteria().andCreatorEqualTo(userid);
        Integer totalCount = (int) questionMapper.countByExample(example);

        pageDTO.setPageLogic(totalCount, page, size);//完成分页逻辑
        if (page < 1) {
            page = 1;
        }
        if (page > pageDTO.getTotalPage()) {
            page = pageDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);
        QuestionModelExample example1 = new QuestionModelExample();
        example1.createCriteria().andCreatorEqualTo(userid);//数据库搜索问题
        List<QuestionModel> questionModels = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (QuestionModel questionModel : questionModels) {
            UserModel userModel = userMapper.selectByPrimaryKey(questionModel.getCreator());
            Register register = registerMapper.selectByPrimaryKey(questionModel.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(questionModel, questionDTO);
            questionDTO.setUserModel(userModel);
            questionDTO.setRegister(register);
            questionDTOList.add(questionDTO);
        }

        pageDTO.setData(questionDTOList);
        return pageDTO;
    }

    //进入 我的问题或首页问题
    public QuestionDTO getByID(Long id) {

        QuestionModel questionModel = questionMapper.selectByPrimaryKey(id);
        if(questionModel == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(questionModel, questionDTO);
        UserModel userModel = userMapper.selectByPrimaryKey(questionModel.getCreator());
        Register register = registerMapper.selectByPrimaryKey(questionModel.getCreator());
        questionDTO.setRegister(register);
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

    public void deleteQuestion(Long id) {
            questionMapper.deleteByPrimaryKey(id);
    }

    public void view(Long id) {//调用方法，传入id
        QuestionModel questionModel = new QuestionModel();
        questionModel.setId(id);
        questionModel.setViewCount(1);
        questionExtModelMapper.view(questionModel);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(),",");

        String regexptag = Arrays.stream(tags).collect(Collectors.joining("|"));
        QuestionModel questionModel = new QuestionModel();
        questionModel.setId(questionDTO.getId());
        questionModel.setTag(regexptag);

        List<QuestionModel> regexpQuestion = questionExtModelMapper.selectRelated(questionModel);
        List<QuestionDTO> questionDTOS = regexpQuestion.stream().map(q -> {

            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);//赋值
             return questionDTO1;

        }).collect(Collectors.toList());
        return questionDTOS;
    }

}
