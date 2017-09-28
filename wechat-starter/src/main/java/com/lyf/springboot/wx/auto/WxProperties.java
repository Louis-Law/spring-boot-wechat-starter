package com.lyf.springboot.wx.auto;

import org.springframework.boot.autoconfigure.social.SocialProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 永锋 on 2016/11/22.
 */
@ConfigurationProperties(
				prefix = "spring.social.wx"
)
public class WxProperties extends SocialProperties {

	private String appName;

	private int deviceType;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
}
