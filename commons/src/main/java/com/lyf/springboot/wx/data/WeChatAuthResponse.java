package com.lyf.springboot.wx.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * right result { "access_token":"ACCESS_TOKEN", "expires_in":7200, "refresh_token":"REFRESH_TOKEN",
 * "openid":"OPENID", "scope":"SCOPE", "unionid":"o6_bmasdasdsad6_2sgVt1231234" }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class WeChatAuthResponse {

  private int errcode;

  private String errmsg;

  private String accessToken;

  private int expiresIn;

  private String refreshToken;

  private String openid;

  private String scope;

  private String unionid;

}
