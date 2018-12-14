package com.snow.ly.blog.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxAccessToken {

    private String access_token;

    private Integer expires_in;

    private String refresh_token;

    private String openid;

    private String scope;

}
