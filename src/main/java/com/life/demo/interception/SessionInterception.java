package com.life.demo.interception;

import com.life.demo.Service.NotificationService;
import com.life.demo.mapper.UserModelMapper;
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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();           //4.请求cookies用request，设置cookies用respond。
        if (cookies != null) {

            for (Cookie cookie : cookies) {                      //5.遍历cookies中所有cookies对象
                if (cookie.getName().equals("token")) {          //6.找到cookie中“token”名字
                    String token = cookie.getValue();

                    UserModelExample usermodelExample = new UserModelExample();//2
                    usermodelExample.createCriteria().andTokenEqualTo(token);//自动拼接到sql.3
                    List<UserModel> userModels = userMapper.selectByExample(usermodelExample);//1.4

                   // UserModel userModel = userMapper.findByToken(token);//2.用token查usermodel//7.在数据库中查是否有token记录
                    if (userModels.size() != 0 ) {//5
                        request.getSession().setAttribute("userModel", userModels.get(0));//8.把userModel放到Session中
                        Long unreadCount = notificationService.unreadCount(userModels.get(0).getId());
                        request.getSession().setAttribute("unreadCount",unreadCount);
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
