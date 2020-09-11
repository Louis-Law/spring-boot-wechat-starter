package com.lyf.springboot.wechat;

import com.lyf.springboot.wx.WxAuthServiceBase;
import com.lyf.springboot.wx.auto.WeChatProperties;
import com.lyf.springboot.wx.data.WeChatAuthResponse;
import com.lyf.springboot.wx.data.WeChatUserInfo;

/**
 * @author
 */
public class ClientAuthService extends WxAuthServiceBase {
    private WxAuthClient wxAuthClient;

    public ClientAuthService(WeChatProperties weChatProperties, WxAuthClient wxAuthClient) {
        super(weChatProperties);
        this.wxAuthClient = wxAuthClient;
    }

    @Override
    public WeChatAuthResponse auth(String authorizationCode) {
        return wxAuthClient.auth(this.weChatProperties.getAppId(), this.weChatProperties.getAppSecret(),
                authorizationCode, WxAuthClient.GRANT_TYPE_AUTH);
    }

    @Override
    public WeChatUserInfo userInfo(String accessToken, String openId) {
        return wxAuthClient.userInfo(accessToken, openId, WxAuthClient.LANG_CHINA);
    }
}
