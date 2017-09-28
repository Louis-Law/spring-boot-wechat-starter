package com.lyf.springboot.wx.auto;

import com.lyf.springboot.wx.WxAuthService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 永锋 on 2017/9/28.
 */
@Configuration
@EnableConfigurationProperties(WxProperties.class)
public class WxAutoConfiguration {
	@Bean(name = "wxAuthService")
	public WxAuthService wxAuthService(){
		return new WxAuthService();
	}
}
