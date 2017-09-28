package com.lyf.springboot.wx;

import com.lyf.springboot.wx.auto.WxProperties;
import com.lyf.springboot.wx.req.WeChatAuthResponse;
import com.lyf.springboot.wx.req.WeChatUserInfo;
import com.lyf.springboot.wx.utils.HttpClientUtil;
import com.lyf.springboot.wx.utils.JsonHelper;
import com.lyf.springboot.wx.utils.WxHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 永锋 on 2017/9/28.
 */
public class WxAuthService {
	private static Logger logger = LoggerFactory.getLogger(WxAuthService.class);
	@Autowired
	private WxProperties wxProperties;

	public WxAuthService() {
	}

	/**
	 * 进行微信授权，获取用户信息 存入进本库 和 关系库
	 * @param code
	 * @return
	 */
	public WeChatUserInfo auth(String code){
		if(StringUtils.isEmpty(code) || "null".equalsIgnoreCase(code)){
			logger.warn("error code: {}", code);
			return null;
		}

		String accessUrl = WxHelper.getWXAccessUrl(code, wxProperties.getAppId(),
						wxProperties.getAppSecret());
		logger.info("accessUrl:{}", accessUrl);
		String result = HttpClientUtil.getHttpsContent(accessUrl);
		WeChatAuthResponse response = JsonHelper.fromJsonString(result, WeChatAuthResponse.class);

		if(response == null || response.getErrcode() != 0 || StringUtils.isBlank(response.getOpenid())){
			return null;
		}
		WeChatUserInfo userInfo = WxHelper.requestUserInfo(response.getAccess_token(), response.getOpenid());
		if(userInfo == null || StringUtils.isBlank(userInfo.getUnionid())){
			return null;
		}
		return userInfo;
	}



}
