package com.life.demo.interception;

import com.life.demo.Service.NotificationService;
import com.life.demo.mapper.RegisterMapper;
import com.life.demo.mapper.UserModelMapper;
import com.life.demo.model.Register;
import com.life.demo.model.RegisterExample;
import com.life.demo.model.UserModel;
import com.life.demo.model.UserModelExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterception implements HandlerInterceptor {
    @Autowired
    private UserModelMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RegisterMapper registerMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    UserModelExample usermodelExample = new UserModelExample();
                    usermodelExample.createCriteria().andTokenEqualTo(token);
                    List<UserModel> userModels = userMapper.selectByExample(usermodelExample);

                    if (userModels.size() != 0 ) {
                        request.getSession().setAttribute("userModel", userModels.get(0));
                        Long unreadCount = notificationService.unreadCount(userModels.get(0).getId());
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }

                    break;
                }

                if ("accountUser".equals(cookie.getName())) {
                    String value = cookie.getValue();

                    RegisterExample example = new RegisterExample();
                    example.createCriteria().andNameEqualTo(value);
                    List<Register> registers = registerMapper.selectByExample(example);

                    if (registers.size() != 0 ) {
                        request.getSession().setAttribute("userModel", registers.get(0));
                      //  Long unreadCount = notificationService.unreadCount(userModels.get(0).getId());
                       // request.getSession().setAttribute("unreadCount",unreadCount);
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
