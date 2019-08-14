package com.life.demo.Controller;

import com.life.demo.Service.QuestionService;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.mapper.QuestionModelMapper;
import com.life.demo.mapper.UserModelMapper;
import com.life.demo.model.QuestionModel;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionModelMapper questionMapper;
    @Autowired
    private UserModelMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String EditPublish(@PathVariable("id") Long id,
                              Model model) {
        QuestionDTO question = questionService.getByID(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }


    @GetMapping("/publish")
    public String publsh() {
        return "publish";
    }

    @PostMapping("/publish")
    public String PostPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model //返回错误信息
    ) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title == "" || description == "" || tag == "") {
            model.addAttribute("error", "标题,内容或标签不能为空");
            return "publish";
        }

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
    }

}

