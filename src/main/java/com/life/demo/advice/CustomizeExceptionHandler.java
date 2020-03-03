package com.life.demo.advice;


import com.alibaba.fastjson.JSON;
import com.life.demo.dto.ResultDTO;
import com.life.demo.exception.CustomizeErrorCode;
import com.life.demo.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice//业务异常
public class CustomizeExceptionHandler {
//当将异常抛到controller时,可以对异常进行统一处理,规定返回的json格式或是跳转到一个错误页面（渲染某个页面模板返回给浏览器）
    @ExceptionHandler(Exception.class)//所有exception都要处理，If you want such methods to apply more globally, across controllers, you can declare them in a class marked with @ControllerAdvice
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model,HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(1000000,"牛，这是作者未想到的json业务异常");
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();//往前端手写错误信息（json）
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
                return null;

        } else {
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", "牛，这是作者未想到的业务异常");
            }
            return new ModelAndView("error");
        }
    }
}
