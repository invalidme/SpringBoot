package com.life.demo.Controller;

import com.life.demo.Service.UserService;
import com.life.demo.dto.AccessTokenDTO;
import com.life.demo.dto.GithubUser;
import com.life.demo.mapper.UserModelMapper;
import com.life.demo.model.UserModel;
import com.life.demo.provider.GithubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


//2.创建AuthorizeController类，创建callback方法用于拿到从1回来的code和state，
//3. 把拿到的2个参数POST到https://github.com/login/oauth/access_token去交换access token。
//为了完成3 创建一个包provider 在包里创建一个GithubProvider类
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired //把Spring容器里的实例化的实例自动加载到当前上下文，把刚才实例化的对象放到githubProvider了
    private GithubProvider githubProvider;

    @Value("${github.client.id}")//去配置文件中读github.client.id的value,赋值到clientId。
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);

        if (user != null) {

            UserModel userModel = new UserModel();
            String token = UUID.randomUUID().toString();//获取用户信息，生成一个token，存到数据库中
            userModel.setToken(token);
            userModel.setName(user.getName());
            userModel.setAccountId(String.valueOf(user.getId()));//强转
            userModel.setAvatarUrl(user.getAvatar_url());
            userService.createORupdate(userModel);
            response.addCookie(new Cookie("token", token));//name和value就是网页可以查看的cookies
            //request.getSession().setAttribute("user",user);//把当前用户放入session，user对象也可以放入
            return "redirect:/";
        } else {
            log.error("callback sign in error,{}", user);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("userModel");
        Cookie cookies = new Cookie("token", null);
        cookies.setMaxAge(0);
        response.addCookie(cookies);
        return "redirect:/";
    }
}


