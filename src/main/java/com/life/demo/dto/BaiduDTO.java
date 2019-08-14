package com.life.demo.dto;

import lombok.Data;

@Data
public class BaiduDTO {
    private String grant_type;
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
}
