package com.life.demo.Controller;

import com.life.demo.Service.QuestionService;
import com.life.demo.dto.PageDTO;
import com.life.demo.mapper.QuestionModelMapper;
import com.life.demo.mapper.UserModelMapper;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {

    @Autowired
    private UserModelMapper userMapper;
    @Autowired
    private QuestionModelMapper questionMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          @PathVariable(name = "action") String action,
                          Model model) {

       /* UserModel userModel = null;
        Cookie[] cookies = request.getCookies();           //4.请求cookies用request，设置cookies用respond。
        if (cookies != null) {

            for (Cookie cookie : cookies) {                      //5.遍历cookies中所有cookies对象
                if (cookie.getName().equals("token")) {          //6.找到cookie中“token”名字
                    String token = cookie.getValue();
                    userModel = userMapper.findByToken(token);//2.//7.在数据库中查是否有token记录
                    if (userModel != null) {
                        request.getSession().setAttribute("userModel", userModel);//8.把userModel放到Session中
                    }
                    break;
                }
            }
        }*/
        UserModel userModel = (UserModel) request.getSession().getAttribute("userModel");
        if (userModel == null) {
            return "redirect:/";
        }


        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");

        }
        if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
        }

        PageDTO pageDTO = questionService.listProfile(userModel.getId(), page, size);
        model.addAttribute("pageDTO", pageDTO);
        return "profile";
    }
}
