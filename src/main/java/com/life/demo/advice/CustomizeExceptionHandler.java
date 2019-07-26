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

@ControllerAdvice//异常的统一操作
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)//所有exception都要处理
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model,HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);//返回json
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
                return null;

        } else {
            //HttpStatus status = getStatus(request);
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());//可以处理的问题
            } else {
                model.addAttribute("message", "服务器炸了！！！");//无法处理的问题
            }
            return new ModelAndView("error");
        }
    }
}
