package com.life.demo.Controller;

import com.life.demo.Service.RegisterService;
import com.life.demo.dto.ResultDTO;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
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
import javax.xml.transform.Result;

@Controller
public class AccountRegister {
    @Autowired
    private RegisterService registerService;

    @GetMapping("/register")
    public String account() {
        return "register";
    }
    @ResponseBody
    @PostMapping("/register")
    public Object PostRegister(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            HttpServletResponse response
    ){
        Register register = new Register();
        register.setPassword(password);
        register.setName(username);

        try {
            registerService.CheckRegister(register);
        }catch (CustomizeException e){
            return ResultDTO.errorOf(e);
        }
//        request.getSession().setAttribute("AccountUser",username);
        response.addCookie(new Cookie("accountUser",username));
        return ResultDTO.okOf();
    }

}



