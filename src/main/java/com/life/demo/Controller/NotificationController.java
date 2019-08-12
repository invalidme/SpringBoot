package com.life.demo.Controller;

import com.life.demo.Service.NotificationService;
import com.life.demo.dto.NotificationDTO;
import com.life.demo.enums.NotificationTypeEnum;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

    @Controller
    public class NotificationController {

        @Autowired
        private NotificationService notificationService;

        @GetMapping("/notification/{id}")
        public String profile(HttpServletRequest request,
                               @PathVariable(name = "id") Long id){
        UserModel userModel = (UserModel) request.getSession().getAttribute("userModel");
        if (userModel == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.readCount(id,userModel);

            if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
            || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/questions/" + notificationDTO.getOuterid() ;
        }else {
            return "redirect:/";
        }
    }
}
