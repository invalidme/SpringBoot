package com.life.demo.Controller;

import com.life.demo.Service.CommentService;
import com.life.demo.Service.QuestionService;
import com.life.demo.dto.CommentDTO;
import com.life.demo.dto.QuestionDTO;
import com.life.demo.enums.CommentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/questions/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getByID(id);
        List<CommentDTO> commentDTOList = commentService.listByCommentId(id, CommentTypeEnum.QUESTION);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
      //累加阅读数
        questionService.view(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("commentDTOList", commentDTOList);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
