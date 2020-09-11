package com.lyf.springboot.wx.service;

import com.lyf.springboot.wx.WxAuthServiceBase;
import com.lyf.springboot.wx.auto.WeChatProperties;
import com.lyf.springboot.wx.data.WeChatAuthResponse;
import com.lyf.springboot.wx.data.WeChatUserInfo;
import com.lyf.springboot.wx.utils.HttpClientUtil;
import com.lyf.springboot.wx.utils.JsonHelper;
import com.lyf.springboot.wx.utils.WxConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 永锋
 * @date 2017/9/28
 */
@Slf4j
public class ClientAuthService extends WxAuthServiceBase {

    public ClientAuthService(WeChatProperties weChatProperties) {
        super(weChatProperties);
    }

    @Override
    public WeChatAuthResponse auth(String authorizationCode) {
        if (StringUtils.isEmpty(authorizationCode) || "null".equalsIgnoreCase(authorizationCode)) {
            log.warn("error code: {}", authorizationCode);
            return null;
        }

        String accessUrl = getWXAccessUrl(authorizationCode, weChatProperties.getAppId(),
                weChatProperties.getAppSecret());
        log.info("accessUrl:{}", accessUrl);
        String result = HttpClientUtil.getHttpsContent(accessUrl);
        WeChatAuthResponse response = JsonHelper.fromJsonString(result, WeChatAuthResponse.class);

        if (response == null || response.getErrcode() != 0 || StringUtils.isBlank(response.getOpenid())) {
            return null;
        }
        return response;
    }

    @Override
    public WeChatUserInfo userInfo(String accessToken, String openId) {
        String userInfoUrl = getWxUserInfoUrl(accessToken, openId);
        log.info("userinfo url: {}", userInfoUrl);
        String userInfo = HttpClientUtil.getHttpsContent(userInfoUrl);
        log.info("result user info: {}", userInfo);
        return JsonHelper.fromJsonString(userInfo, WeChatUserInfo.class);
    }

    private static String getWXAccessUrl(String code, String appId, String secret) {
        return WxConstants.API_HOST + WxConstants.AUTH_PATH + "?appid=" + appId + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
    }

    private static String getWxUserInfoUrl(String accessToken, String openid) {
        return WxConstants.API_HOST + WxConstants.USER_INFO_PATH + "?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
    }
}
