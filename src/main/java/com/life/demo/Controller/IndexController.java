package com.life.demo.Controller;

import com.life.demo.Service.QuestionService;
import com.life.demo.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "7") Integer size,
                        @RequestParam(name = "search", required= false) String search
    ) {
        PageDTO pageDTOs = questionService.list(search, page, size);
        model.addAttribute("search",search);
        model.addAttribute("pageDTOs", pageDTOs);
        return "index";

    }
}
