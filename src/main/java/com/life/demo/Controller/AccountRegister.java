package com.life.demo.Controller;

import com.life.demo.Service.RegisterService;
import com.life.demo.dto.ResultDTO;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.model.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AccountRegister {
    @Autowired
    private RegisterService registerService;

    @GetMapping("/register")
    public String account() {
        return "register";
    }
    @PostMapping("/register")
    public Object PostRegister(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        model.addAttribute("username", username);
        model.addAttribute("password", password);

        Register register = new Register();
        register.setPassword(password);
        register.setName(username);

        registerService.CheckRegister(register);
//        request.getSession().setAttribute("AccountUser",username);
        response.addCookie(new Cookie("accountUser",username));
        return "redirect:/";
    }

}



