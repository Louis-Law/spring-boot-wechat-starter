package com.lyf.springboot.wechat;

import com.lyf.springboot.wx.WxAuthServiceBase;
import com.lyf.springboot.wx.auto.WeChatProperties;
import com.lyf.springboot.wx.auto.WxAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用feign client 访问微信
 *
 * @author yongfeng.liu
 */
@Configuration
@EnableFeignClients(clients = {WxAuthClient.class})
@AutoConfigureAfter(WxAutoConfiguration.class)
public class WxFeignConfiguration {

    @Bean
    @ConditionalOnMissingBean(WxAuthServiceBase.class)
    public WxAuthServiceBase wxAuthService(WeChatProperties chatProperties, WxAuthClient wxAuthClient) {
        return new ClientAuthService(chatProperties, wxAuthClient);
    }
}
