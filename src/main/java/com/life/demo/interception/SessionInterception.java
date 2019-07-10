package com.life.demo.interception;

import com.life.demo.mapper.UserMapper;
import com.life.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class SessionInterception implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();           //4.请求cookies用request，设置cookies用respond。
        if (cookies != null) {

            for (Cookie cookie : cookies) {                      //5.遍历cookies中所有cookies对象
                if (cookie.getName().equals("token")) {          //6.找到cookie中“token”名字
                    String token = cookie.getValue();
                    UserModel userModel = userMapper.findByToken(token);//2.//7.在数据库中查是否有token记录
                    if (userModel != null) {
                        request.getSession().setAttribute("userModel", userModel);//8.把userModel放到Session中
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
