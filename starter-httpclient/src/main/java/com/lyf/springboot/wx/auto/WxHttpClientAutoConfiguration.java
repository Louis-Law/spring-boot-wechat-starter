package com.lyf.springboot.wx.auto;

import com.lyf.springboot.wx.WxAuthServiceBase;
import com.lyf.springboot.wx.service.ClientAuthService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author 永锋
 * @date 2017/9/28
 */
@Configuration
@AutoConfigureAfter(WxAutoConfiguration.class)
public class WxHttpClientAutoConfiguration {
	@Bean(name = "wxAuthService")
	@ConditionalOnMissingBean(WxAuthServiceBase.class)
	public WxAuthServiceBase wxAuthService(WeChatProperties weChatProperties){
		return new ClientAuthService(weChatProperties);
	}
}
