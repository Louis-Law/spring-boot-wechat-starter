package com.lyf.springboot.wx.utils;

import com.lyf.springboot.wx.req.WeChatUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 永锋 on 2016/11/23.
 */
public class WxHelper {
	private static final String WX_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
	private static final String WX_ACCESS_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	private static final String MEDIA_DOWN_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

	private static Logger logger = LoggerFactory.getLogger(WxHelper.class);

	public static WeChatUserInfo requestUserInfo(String token, String openId) {
		String userInfoUrl = getWxUserInfoUrl(token, openId);
		logger.info("userinfo url: {}", userInfoUrl);
		String userInfo = HttpClientUtil.getHttpsContent(userInfoUrl);
		logger.info("result user info: {}", userInfo);
		WeChatUserInfo wechatUserInfo = JsonHelper.fromJsonString(userInfo, WeChatUserInfo.class);
		return wechatUserInfo;
	}

	public static final String WX_LOGIN_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s"
					+ "&redirect_uri=%s&response_type=code&scope=snsapi_userinfo";

	public static String getWXAccessUrl(String code, String appId, String secret){
		return String.format(WX_ACCESS_URL, appId, secret, code);
	}
	private static String getWxUserInfoUrl(String accessToken, String openid) {
		return String.format(WX_USER_INFO_URL, accessToken, openid);
	}
}
