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
    ) {
        //List<QuestionDTO> questionList=questionService.list(page,size);
        PageDTO pageDTOs = questionService.list(search, page, size);
        model.addAttribute("search",search);
        model.addAttribute("pageDTOs", pageDTOs);//s:变量名字  //此时返回到前端有questionModel信息和userModel信息
        return "index";

    }
}
