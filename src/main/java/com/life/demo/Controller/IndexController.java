package com.life.demo.Controller;

import com.life.demo.mapper.QuestionMapper;
import com.life.demo.mapper.UserMapper;
import com.life.demo.model.QuestionModel;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;                         //1.通过UserMapper访问数据库中的内容

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {       //3.注入http获取2.的token
        Cookie[] cookies = request.getCookies();           //4.请求cookies用request，设置cookies用respond。
        if (cookies != null) {

            for (Cookie cookie : cookies) {                      //5.遍历cookies中所有cookies对象
                if (cookie.getName().equals("token")) {          //6.找到cookie中“token”名字
                    String token = cookie.getValue();
                    UserModel userModel = userMapper.findByToken(token);//2.//7.在数据库中查是否有token记录
                    if (userModel != null) {
                        request.getSession().setAttribute("userModel", userModel);//8.把userModel放到Session中
                    }
                    break;
                }
            }
        }


        List<QuestionModel> questionList=questionMapper.list();
        model.addAttribute("questions",questionList);//s:变量名字
            return "index";

    }
}
