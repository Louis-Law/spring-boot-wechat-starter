package com.lyf.springboot.wx.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author 永锋
 * @date 2016/11/23
 */
public interface WxConstants {
    String API_HOST = "https://api.weixin.qq.com";
    String AUTH_PATH = "/sns/oauth2/access_token";
    String USER_INFO_PATH = "/sns/userinfo";

    String MEDIA_DOWN_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    String WX_LOGIN_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s"
            + "&redirect_uri=%s&response_type=code&scope=snsapi_userinfo";


    /**
     * 微信授权链接
     * @param appId
     * @param redirectUrl
     * @return
     * @throws UnsupportedEncodingException
     */
    static String loginUrl(String appId, String redirectUrl) throws UnsupportedEncodingException {
        return String.format(WxConstants.WX_LOGIN_URL, appId, URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.name()));
    }
}
