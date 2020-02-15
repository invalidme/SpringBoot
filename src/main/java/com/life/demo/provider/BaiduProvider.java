package com.life.demo.provider;

import com.alibaba.fastjson.JSON;
import com.life.demo.dto.BaiduDTO;
import com.life.demo.dto.BaiduUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BaiduProvider {
    //https://square.github.io/okhttp/
    public String getAccessToken(BaiduDTO baiduDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String urlString = "https://openapi.baidu.com/oauth/2.0/token?grant_type=" + baiduDTO.getGrant_type() +
                "&code=" + baiduDTO.getCode() + "&client_id=" + baiduDTO.getClient_id() + "&client_secret=" +
                baiduDTO.getClient_secret() + "&redirect_uri=" + baiduDTO.getRedirect_uri();

        //RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(baiduDTO));

        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);

            String accessToken = JSON.parseObject(string).getString("access_token");
            return accessToken;
        } catch (IOException e) {

        }
        return null;
    }

    public BaiduUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://openapi.baidu.com/rest/2.0/passport/users/getInfo?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            BaiduUser baiduUser = JSON.parseObject(string, BaiduUser.class);
            return baiduUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

