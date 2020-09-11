package com.lyf.springboot.wx.demo;

import com.lyf.springboot.wx.WxAuthServiceBase;
import com.lyf.springboot.wx.data.WeChatAuthResponse;
import com.lyf.springboot.wx.data.WeChatUserInfo;
import org.springframework.stereotype.Service;

/**
 * DemoService
 *
 * @author yongfeng.liu
 */
@Service
public class DemoService {
    private final WxAuthServiceBase wxAuthService;

    public DemoService(WxAuthServiceBase wxAuthService) {
        this.wxAuthService = wxAuthService;
    }

    public WeChatUserInfo authAndUser(String code){
        WeChatAuthResponse authResponse = wxAuthService.auth(code);
        if(authResponse == null) {
            return null;
        }
        return wxAuthService.userInfo(authResponse.getAccessToken(), authResponse.getOpenid());
    }

}
