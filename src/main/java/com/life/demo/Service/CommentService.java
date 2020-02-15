package com.life.demo.Service;

import com.life.demo.dto.CommentDTO;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.enums.CommentTypeEnum;
import com.life.demo.enums.NotificationStatusEnum;
import com.life.demo.enums.NotificationTypeEnum;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
import com.life.demo.mapper.*;
import com.life.demo.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentModelMapper commentModelMapper;
    @Autowired
    private QuestionModelMapper questionModelMapper;
    @Autowired
    private QuestionExtModelMapper questionExtModelMapper;
    @Autowired
    private UserModelMapper userModelMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private CommentExtModelMapper commentExtModelMapper;
    @Autowired
    private RegisterMapper registerMapper;
    @Transactional//把整个方法体增加一个事务
    public void insert(CommentModel commentModel,QuestionDTO commentator) {
        if (commentModel.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);//创建异常对象，手动抛出
        }
        if (commentModel.getType() == null || !CommentTypeEnum.isExist(commentModel.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (commentModel.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            CommentModel dbComment = commentModelMapper.selectByPrimaryKey(commentModel.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            QuestionModel questionModel = questionModelMapper.selectByPrimaryKey(dbComment.getParentId());
            if(questionModel == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentModelMapper.insert(commentModel);
            //增加评论的评论数
            CommentModel parentComment = new CommentModel();
            parentComment.setId(commentModel.getParentId());
            parentComment.setCommentCount(1);
            commentExtModelMapper.CommentCount(parentComment);

            //创建通知
            if(commentator.getUserModel() != null )
                createNotify(commentModel, dbComment.getCommentator(), commentator.getUserModel().getName(), questionModel.getTitle(), NotificationTypeEnum.REPLY_COMMENT, questionModel.getId());
            else
                createNotify(commentModel, dbComment.getCommentator(), commentator.getRegister().getName(), questionModel.getTitle(), NotificationTypeEnum.REPLY_COMMENT, questionModel.getId());

        } else {
            //回复问题
            QuestionModel questionModel = questionModelMapper.selectByPrimaryKey(commentModel.getParentId());
            if(questionModel == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentModel.setCommentCount(0);
            commentModelMapper.insert(commentModel);
            questionModel.setCommentCount(1);
            questionExtModelMapper.CommentCount(questionModel);

            //创建通知
            if(commentator.getUserModel() != null )
                createNotify(commentModel,questionModel.getCreator(),commentator.getUserModel().getName(),questionModel.getTitle(), NotificationTypeEnum.REPLY_QUESTION, questionModel.getId());
            else
                createNotify(commentModel,questionModel.getCreator(),commentator.getRegister().getName(),questionModel.getTitle(), NotificationTypeEnum.REPLY_QUESTION, questionModel.getId());
        }
    }

    private void createNotify(CommentModel commentModel, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());

        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);//回复问题的id
        notification.setNotifier(commentModel.getCommentator());//当前评论人
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());

        notification.setReceiver(receiver);//回复的问题的创建者的名字

        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);

        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByCommentId(Long id, CommentTypeEnum type){
        CommentModelExample commentModelExample = new CommentModelExample();
        commentModelExample.createCriteria().andParentIdEqualTo(id)
                                            .andTypeEqualTo(type.getType());
        //按时间顺序排列 数据库语句
        commentModelExample.setOrderByClause("gmt_create desc");
        List<CommentModel> commentLists = commentModelMapper.selectByExample(commentModelExample);

        if(commentLists.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = commentLists.stream().map(commentModel -> commentModel.getCommentator()).collect(Collectors.toSet());//整个Collectors工具类就是在为Collector服务，用于创建各种不同的Collector
        List<Long> userIds = new ArrayList<>();                                                                                        //将流中的元素放置到一个无序集set中去。默认为HashSet。
        userIds.addAll(commentators);

        //获取评论人user表信息并转换为 Map
        UserModelExample userModelExample = new UserModelExample();
        userModelExample.createCriteria().andIdIn(userIds);
        List<UserModel> userModels= userModelMapper.selectByExample(userModelExample);
        Map<Long, UserModel> userModelMap = userModels.stream().collect(Collectors.toMap(userModel -> userModel.getId(), userModel -> userModel));

        RegisterExample registerExample  = new RegisterExample();
        userModelExample.createCriteria().andIdIn(userIds);
        List<Register> registers= registerMapper.selectByExample(registerExample);
        Map<Long,Register> registerMap = registers.stream().collect(Collectors.toMap(register->register.getId(), register -> register));

            List<CommentDTO> commentDTOS = commentLists.stream().map(commentModel -> {
                CommentDTO commentDTO =new CommentDTO();
                BeanUtils.copyProperties(commentModel,commentDTO);
                commentDTO.setRegister(registerMap.get(commentModel.getCommentator()));
                commentDTO.setUserModel(userModelMap.get(commentModel.getCommentator()));
                return commentDTO;
            }).collect(Collectors.toList());
            return commentDTOS;
        }



}
