package com.lyf.springboot.wx.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Arrays;

/**
 * {
 *  "openid":"omZu7t_MZ48ZeXLv6HV77z8_xxx",
 *  "nickname":"蒼白",
 *  "sex":1,
 *  "language":"zh_CN",
 *  "city":"西城",
 *  "province":"北京",
 *  "country":"中国",
 *  "headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/ajNVdqHZLLBSVTcnqBd5s47NbibExsxoRlo0uVfppEBbjNmgUxSnTdAXtS8pcpZMicVsTO9VpwVv2aexOZxXODUQ\/0",
 *  "privilege":[],
 *  "unionid":"oXqrGt6JRRZJDQDEQAFUkabc"
 *  }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class WeChatUserInfo {

  private static final int SEX_MAN = 1;

  private static final int SEX_WOMEN = 2;

  private String openid;

  private String nickname;

  private int sex;

  private String province;

  private String city;

  private String country;

  private String headimgurl;

  private String srcimg;

  private String[] privilege;

  private String unionid;

  private String language;

  public void beMan() {
    this.sex = SEX_MAN;
  }
  public void beWomen() {
    this.sex = SEX_WOMEN;
  }

  @Override
  public String toString() {
    return "WechatUserInfo{" +
           "openid='" + openid + '\'' +
           ", nickname='" + nickname + '\'' +
           ", sex=" + sex +
           ", province='" + province + '\'' +
           ", city='" + city + '\'' +
           ", country='" + country + '\'' +
           ", headimgurl='" + headimgurl + '\'' +
           ", privilege=" + Arrays.toString(privilege) +
           ", unionid='" + unionid + '\'' +
           '}';
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getSrcimg() {
    return srcimg;
  }

  public void setSrcimg(String srcimg) {
    this.srcimg = srcimg;
  }
}
