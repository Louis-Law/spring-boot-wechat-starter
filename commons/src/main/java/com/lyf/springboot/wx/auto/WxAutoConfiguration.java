package com.lyf.springboot.wx.auto;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author 永锋
 * @date 2017/9/28
 */
@Configuration
@EnableConfigurationProperties(WeChatProperties.class)
public class WxAutoConfiguration {

}
