package com.lyf.springboot.wechat;

import com.lyf.springboot.wx.data.WeChatAuthResponse;
import com.lyf.springboot.wx.data.WeChatUserInfo;
import com.lyf.springboot.wx.utils.WxConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 */

@FeignClient(name = "wxAuthClient", url = WxConstants.API_HOST)
public interface WxAuthClient {
    String GRANT_TYPE_AUTH = "authorization_code";
    String LANG_CHINA = "zh_CN";


    @GetMapping(WxConstants.AUTH_PATH)
    WeChatAuthResponse auth(@RequestParam("appid") String appId,
                            @RequestParam("secret") String secret,
                            @RequestParam("code") String authorizationCode,
                            @RequestParam("grant_type") String grantType);

    @GetMapping(WxConstants.USER_INFO_PATH)
    WeChatUserInfo userInfo(@RequestParam("openid") String openId,
                            @RequestParam("access_token") String accessToken,
                            @RequestParam("lang") String lang);
}
