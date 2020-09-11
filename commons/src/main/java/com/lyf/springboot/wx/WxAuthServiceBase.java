package com.lyf.springboot.wx;

import com.lyf.springboot.wx.auto.WeChatProperties;
import com.lyf.springboot.wx.data.WeChatAuthResponse;
import com.lyf.springboot.wx.data.WeChatUserInfo;

/**
 * Created by 永锋 on 2017/9/28.
 */
public abstract class WxAuthServiceBase {
    protected WeChatProperties weChatProperties;

    public WxAuthServiceBase(WeChatProperties weChatProperties) {
        this.weChatProperties = weChatProperties;
    }

    /**
     * 微信授权
     *
     * @param authorizationCode
     * @return
     */
    public abstract WeChatAuthResponse auth(String authorizationCode);

    /**
     * 获取用户的微信个人信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public abstract WeChatUserInfo userInfo(String accessToken, String openId);

}
