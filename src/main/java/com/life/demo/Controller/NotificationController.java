package com.life.demo.Controller;

import com.life.demo.Service.NotificationService;
import com.life.demo.dto.NotificationDTO;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.enums.NotificationTypeEnum;
import com.life.demo.model.Register;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

    @Controller
    public class NotificationController {

        @Autowired
        private NotificationService notificationService;

        @GetMapping("/notification/{id}")
        public String profile(HttpServletRequest request,
                               @PathVariable(name = "id") Long id){
            QuestionDTO questionDTO = new QuestionDTO();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        UserModel userModel = (UserModel) request.getSession().getAttribute("userModel");
                        questionDTO.setUserModel(userModel);
                        NotificationDTO notificationDTO = notificationService.readCount(id, questionDTO);

                        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType())
                            return "redirect:/questions/" + notificationDTO.getOuterid();
                        else
                            return "redirect:/";

                    } else if(cookie.getName().equals("accountUser")){

                        Register register = (Register) request.getSession().getAttribute("userModel");
                        questionDTO.setRegister(register);
                        NotificationDTO notificationDTO = notificationService.readCount(id, questionDTO);

                        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType())
                            return "redirect:/questions/" + notificationDTO.getOuterid();
                        else
                            return "redirect:/";
                    }
                }
            }
            return null;
    }
}
