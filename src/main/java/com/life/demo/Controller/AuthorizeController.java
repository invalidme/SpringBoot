package com.life.demo.Controller;

import com.life.demo.dto.AccessTokenDTO;
import com.life.demo.dto.GithubUser;
import com.life.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


//2.创建AuthorizeController类，创建callback方法用于拿到从1回来的code和state，
//3. 把拿到的2个参数POST到https://github.com/login/oauth/access_token去交换access token。
//为了完成3 创建一个包provider 在包里创建一个GithubProvider类
@Controller
public class AuthorizeController {

    @Autowired //把Spring容器里的实例化的实例自动加载到当前上下文，把刚才实例化的对象放到githubProvider了
    private GithubProvider githubProvider;

    @Value("${github.client.id}")//去配置文件中读github.client.id的value,赋值到clientId。
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state")String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);

        //6.调用已封装的githubProvider
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
