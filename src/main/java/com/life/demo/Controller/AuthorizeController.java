package com.life.demo.Controller;

import com.life.demo.Service.UserService;
import com.life.demo.dto.AccessTokenDTO;
import com.life.demo.dto.GithubUser;
import com.life.demo.model.UserModel;
import com.life.demo.provider.GithubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
@Slf4j
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
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
            String token = UUID.randomUUID().toString();
            userModel.setToken(token);
            userModel.setName(user.getName());
            userModel.setAccountId(String.valueOf(user.getId()));
            userModel.setAvatarUrl(user.getAvatar_url());
            userService.createORupdate(userModel);
            response.addCookie(new Cookie("token", token));
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
//        request.getSession().removeAttribute("accountUser");

        Cookie userCookie = new Cookie("token", null);
        Cookie accountUserCookie = new Cookie("accountUser", null);

        userCookie.setMaxAge(0);
        accountUserCookie.setMaxAge(0);

        response.addCookie(userCookie);
        response.addCookie(accountUserCookie);
        return "redirect:/";
    }
}


