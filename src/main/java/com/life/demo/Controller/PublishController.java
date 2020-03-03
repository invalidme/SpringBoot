package com.life.demo.Controller;

import com.life.demo.Service.QuestionService;
import com.life.demo.cache.TagCache;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
import com.life.demo.mapper.QuestionModelMapper;
import com.life.demo.mapper.UserModelMapper;
import com.life.demo.model.QuestionModel;
import com.life.demo.model.Register;
import com.life.demo.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")//修改
    public String EditPublish(@PathVariable("id") Long id,
                              Model model,
                              HttpServletRequest request) {
        QuestionDTO question = questionService.getByID(id);
        UserModel userModel = (UserModel) request.getSession().getAttribute("userModel");
        if(!userModel.getId().equals(question.getCreator())){
            throw new CustomizeException(CustomizeErrorCode.MODIFY_QUESTION);
        }
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());

        model.addAttribute("tags", TagCache.get());

        return "publish";
    }


    @GetMapping("/publish")//GET渲染页面
    public String publsh(Model model) {
        //规范标签
        model.addAttribute("tags", TagCache.get());
        return "publish";

    }

    @PostMapping("/publish")//post执行请求
    public String PostPublish(
            @RequestParam(value = "title", required = false) String title,//接收参数
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model //返回错误信息
    ) {
        //回显，向前端传值，防止丢失用户输入的内容
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","存在非法标签:"+ invalid);
            return "publish";
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    UserModel userModel = (UserModel) request.getSession().getAttribute("userModel");
                    if (userModel == null) {
                        model.addAttribute("error", "用户未登录");
                        return "publish";
                    }
                    QuestionModel questionModel = new QuestionModel();
                    questionModel.setTitle(title);
                    questionModel.setDescription(description);
                    questionModel.setTag(tag);
                    questionModel.setCreator(userModel.getId());
                    questionModel.setGmtCreate(System.currentTimeMillis());
                    questionModel.setId(id);
                    questionService.createUpdate(questionModel);
                    return "redirect:/";
                }else if (cookie.getName().equals("accountUser")){
                    Register rgister  = (Register) request.getSession().getAttribute("userModel");
                    if (rgister == null) {
                        model.addAttribute("error", "用户未登录");
                        return "publish";
                    }
                    QuestionModel questionModel = new QuestionModel();
                    questionModel.setTitle(title);
                    questionModel.setDescription(description);
                    questionModel.setTag(tag);
                    questionModel.setCreator(rgister.getId());
                    questionModel.setGmtCreate(System.currentTimeMillis());
                    questionModel.setId(id);
                    questionService.createUpdate(questionModel);
                    return "redirect:/";
                }
            }
        }
        return null;
    }


    @GetMapping("/publish/delete/{id}")
    public String DeletePublish(
            @PathVariable(value = "id", required = false) Long id
    ){
    questionService.deleteQuestion(id);
    return "redirect:/";
    }

}

