package com.life.demo.provider;

import com.alibaba.fastjson.JSON;
import com.life.demo.dto.AccessTokenDTO;
import com.life.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
//4.创建一个getAccessToken类,包含5个参数,所以创建一个dto包,创建AccessTokenDTO类封装这5个参数.
@Component  // GithubProvider类自动实例化到池子里，通过名字直接用
public class GithubProvider {
public String getAccessToken(AccessTokenDTO accessTokenDTO) {

   //5.从okhttp网站中复制Post to a Server方法（记得引入依赖放到pom.xml）
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));//通过JSON方式传accessTokenDTO
                                                                                        //把accessTokenDTO类转换为JSON
                                                                                        //需要引入依赖,到mvnrepository
    Request request = new Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(body)
            .build();
    try (Response response = client.newCall(request).execute()) {
        String string= response.body().string();

        //String[] split =String.split("&");
       // String tokenstr = split[0];
        String token =string.split("&")[0].split("=")[1];
        return token;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


public GithubUser getUser(String accessToken ){
    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("https://api.github.com/user?access_token="+accessToken)
            .build();
    try {
        Response response  = client.newCall(request).execute();
        String string=response.body().string();
        GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//parseObject自动将string转换成java类对象
        return githubUser;
    } catch (IOException e) {
    }
    return null;
}
}



