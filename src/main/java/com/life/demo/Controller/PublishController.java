package com.life.demo.Controller;

import com.life.demo.Service.QuestionService;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.mapper.QuestionMapper;
import com.life.demo.mapper.UserMapper;
import com.life.demo.model.QuestionModel;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String EditPublish(@PathVariable("id") Integer id,
                              Model model){
        QuestionDTO question = questionService.getByID(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }



    @GetMapping("/publish")
    public String publsh(){
        return "publish";
    }
    @PostMapping("/publish")
    public String PostPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false ) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Integer id,
            HttpServletRequest request,
            Model model //返回错误信息
    ) {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == "" || description == "" || tag== "" ){
            model.addAttribute("error","标题,内容或标签不能为空");
            return "publish";
        }


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
            }*/
        UserModel userModel = (UserModel) request.getSession().getAttribute("userModel");
            if(userModel == null){
                model.addAttribute("error","用户未登录");
                return "publish";
            }
            QuestionModel questionModel = new QuestionModel();
            questionModel.setTitle(title);
            questionModel.setDescription(description);
            questionModel.setTag(tag);
            questionModel.setCreator(userModel.getId());
            questionModel.setGmtcreate(System.currentTimeMillis());

            questionModel.setId(id);

            questionService.createUpdate(questionModel);
            return "redirect:/";
        }

    }

