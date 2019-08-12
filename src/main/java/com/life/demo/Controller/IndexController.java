package com.life.demo.Controller;

import com.life.demo.Service.QuestionService;
import com.life.demo.dto.PageDTO;
import com.life.demo.mapper.QuestionModelMapper;
import com.life.demo.mapper.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserModelMapper userMapper;                         //1.通过UserMapper访问数据库中的内容

    @Autowired
    private QuestionModelMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required= false) String search
    ) {       //3.注入http获取2.的token
     /*   Cookie[] cookies = request.getCookies();           //4.请求cookies用request，设置cookies用respond。
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
        }*/

        //注入QuestionMapper，通过QM写一个能把列表获取到的方法
        //List<QuestionDTO> questionList=questionService.list(page,size);
        PageDTO pageDTOs = questionService.list(search, page, size);
        model.addAttribute("search",search);
        model.addAttribute("pageDTOs", pageDTOs);//s:变量名字  //此时返回到前端有questionModel信息和userModel信息
        return "index";

    }
}
