package com.life.demo.Controller;

import com.life.demo.Service.UserService;
import com.life.demo.dto.BaiduDTO;
import com.life.demo.dto.BaiduUser;
import com.life.demo.mapper.UserModelMapper;
import com.life.demo.model.UserModel;
import com.life.demo.provider.BaiduProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class BaiduController {

    @Autowired
    private BaiduProvider baiduProvider;
    @Autowired
    private UserModelMapper userModelMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/BaiDuCallBack")
    public String BDcallback(@RequestParam(name = "code") String code,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        BaiduDTO baiduDTO = new BaiduDTO();
        baiduDTO.setCode(code);
        baiduDTO.setRedirect_uri("http://localhost:8080/BaiDuCallBack");
        baiduDTO.setClient_id("axkEP12v2NzHOWhXZU5EDfPG");
        baiduDTO.setClient_secret("CbNosivY07xG1HZxcYRKYkiSMZpxQW5H");
        baiduDTO.setGrant_type("authorization_code");
        String accessToken = baiduProvider.getAccessToken(baiduDTO);
        BaiduUser baiduUser = baiduProvider.getUser(accessToken);

        if (baiduUser != null) {
            UserModel userModel = new UserModel();
            String token = UUID.randomUUID().toString();
            userModel.setToken(token);
            userModel.setName(baiduUser.getUsername());
            userModel.setAccountId(String.valueOf(baiduUser.getUserid()));
            userModel.setAvatarUrl(baiduUser.getPortrait());
            userService.createORupdate(userModel);
            response.addCookie(new Cookie("token", token));
            //request.getSession().setAttribute("user", baiduUser);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    }

