package com.life.demo.Controller;

import com.life.demo.mapper.QuestionMapper;
import com.life.demo.mapper.UserMapper;
import com.life.demo.model.QuestionModel;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publsh(){
        return "publish";
    }
    @PostMapping("/publish")
    public String PostPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
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


        UserModel userModel = null;
        Cookie[] cookies = request.getCookies();           //4.请求cookies用request，设置cookies用respond。
        if (cookies != null) {

            for (Cookie cookie : cookies) {                      //5.遍历cookies中所有cookies对象
                if (cookie.getName().equals("token")) {          //6.找到cookie中“token”名字
                    String token = cookie.getValue();
                    userModel = userMapper.findByToken(token);//2.//7.在数据库中查是否有token记录
                    if (userModel != null) {
                        request.getSession().setAttribute("userModel", userModel);//8.把userModel放到Session中

                        QuestionModel questionModel = new QuestionModel();
                        questionModel.setTitle(title);
                        questionModel.setDescription(description);
                        questionModel.setTag(tag);
                        questionModel.setCreator(userModel.getId());
                        questionModel.setGmtcreate(System.currentTimeMillis());
                        questionModel.setGmtmodified(questionModel.getGmtcreate());
                        questionMapper.create(questionModel);

                    }
                    break;
                }


            }
            return "redirect:/";
        } else{
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
    }
}
