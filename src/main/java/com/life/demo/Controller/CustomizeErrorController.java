package com.life.demo.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
//无法拦截的错误
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements  ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = getStatus(request);

        if (status.is4xxClientError()) {
            model.addAttribute("message", "你这个请求错了吧，要不然换个姿势？404not found");
        }
        if (status.is5xxServerError()) {
            model.addAttribute("message", "服务冒烟了，要不然你稍后再试试！！！5XX");
        }

        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {//AbstractErrorController.java 82
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
