package com.life.demo.Service;

import com.life.demo.dto.NotificationDTO;
import com.life.demo.dto.PageDTO;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.enums.NotificationStatusEnum;
import com.life.demo.enums.NotificationTypeEnum;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
import com.life.demo.mapper.NotificationMapper;
import com.life.demo.model.Notification;
import com.life.demo.model.NotificationExample;
import com.life.demo.model.UserModel;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PageDTO list(Long userid, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userid);
        Integer allcount = (int) notificationMapper.countByExample(notificationExample);//往example里传一个userid

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

        NotificationExample example1 = new NotificationExample();//把查询条件封装到这里
        example1.createCriteria().andReceiverEqualTo(userid);
        example1.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));

        if(notifications.size() == 0){
            return pageDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for(Notification notification : notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }


       /* Set<Long> disUserIds = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>(disUserIds);
        UserModelExample userModelExample = new UserModelExample();
        userModelExample.createCriteria().andIdIn(userIds);
        List<UserModel> userModels = userModelMapper.selectByExample(userModelExample);
        Map<Long, UserModel> userModelMap = userModels.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));*/


        //List<QuestionModel> questionModels = questionMapper.listProfile(userid,offset,size);//1.通过questionMapper.list（）查到所有的questionModel对象 //每一页的列表

        pageDTO.setData(notificationDTOS);
        return pageDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().
                andReceiverEqualTo(userId).
                andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return  notificationMapper.countByExample(example);
    }


    public NotificationDTO readCount(Long id, QuestionDTO questionDTO) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        if( questionDTO.getUserModel()!=null  && !Objects.equals(notification.getReceiver(),questionDTO.getUserModel().getId())){//notification.getReceiver() != userModel.getId()
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        if( questionDTO.getRegister() !=null  && !Objects.equals(notification.getReceiver(),questionDTO.getRegister().getId())){//notification.getReceiver() != userModel.getId()
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
            notification.setStatus(NotificationStatusEnum.READ.getStatus());
            notificationMapper.updateByPrimaryKey(notification);

            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            return notificationDTO;
    }
}
