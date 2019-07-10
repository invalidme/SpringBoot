package com.life.demo.Controller;

import com.life.demo.Service.QuestionService;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions/{id}")
    public String question(@PathVariable(name="id") Integer id,
                            Model model){

        QuestionDTO questionDTO = questionService.getByID(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
