package com.lyf.springboot.wx.req;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

/**
 * {
 *  "openid":"omZu7t_MZ48ZeXLv6HV77z8_8dyg",
 *  "nickname":"蒼白",
 *  "sex":1,
 *  "language":"zh_CN",
 *  "city":"西城",
 *  "province":"北京",
 *  "country":"中国",
 *  "headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/ajNVdqHZLLBSVTcnqBd5s47NbibExsxoRlo0uVfppEBbjNmgUxSnTdAXtS8pcpZMicVsTO9VpwVv2aexOZxXODUQ\/0",
 *  "privilege":[],
 *  "unionid":"oXqrGt6JRRZJDQDEQAFUkJJ4tyk0"
 *  }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getHeadimgurl() {
    return headimgurl;
  }

  public void setHeadimgurl(String headimgurl) {
    this.headimgurl = headimgurl;
  }

  public String[] getPrivilege() {
    return privilege;
  }

  public void setPrivilege(String[] privilege) {
    this.privilege = privilege;
  }

  public String getUnionid() {
    return unionid;
  }

  public void setUnionid(String unionid) {
    this.unionid = unionid;
  }

  public void beAman() {
    this.sex = SEX_MAN;
  }
  public void beAwomen() {
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
