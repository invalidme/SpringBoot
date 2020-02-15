package com.life.demo.Controller;

import com.life.demo.Service.NotificationService;
import com.life.demo.Service.QuestionService;
import com.life.demo.dto.PageDTO;
import com.life.demo.model.Register;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          @PathVariable(name = "action") String action,
                          Model model) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    UserModel userModel = (UserModel) request.getSession().getAttribute("userModel");
                    if ("questions".equals(action)) {
                        model.addAttribute("section", "questions");
                        model.addAttribute("sectionName", "我的提问");
                        PageDTO pageDTO = questionService.listProfile(userModel.getId(), page, size);
                        model.addAttribute("pageDTO", pageDTO);

                    }else if ("replies".equals(action)) {
                        PageDTO pageDTO = notificationService.list(userModel.getId(),page,size);
                        Long unreadCount = notificationService.unreadCount(userModel.getId());
                        model.addAttribute("section", "replies");
                        model.addAttribute("pageDTO", pageDTO);
                        model.addAttribute("sectionName", "最新回复");
                    }
                    return "profile";
                }
                else if(cookie.getName().equals("accountUser")){
                    Register register = (Register) request.getSession().getAttribute("userModel");
                    if ("questions".equals(action)) {
                        model.addAttribute("section", "questions");
                        model.addAttribute("sectionName", "我的提问");
                        PageDTO pageDTO = questionService.listProfile(register.getId(), page, size);
                        model.addAttribute("pageDTO", pageDTO);

                    }else if ("replies".equals(action)) {
                        PageDTO pageDTO = notificationService.list(register.getId(),page,size);
                        Long unreadCount = notificationService.unreadCount(register.getId());
                        model.addAttribute("section", "replies");
                        model.addAttribute("pageDTO", pageDTO);
                        model.addAttribute("sectionName", "最新回复");
                    }
                    return "profile";
                }
            }
        }
        return null;
    }
}
