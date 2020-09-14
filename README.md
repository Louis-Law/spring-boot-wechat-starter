## spring-boot-wechat-starter

该项目主要功能是微信授权登陆及获取用户信息，共有两个模块
- wechat-starter 主要功能模块
- demo 测试模块
###wechat-starter模块
#### jar依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-autoconfigure</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
</dependency>
<dependency>
    <groupId>commons-collections</groupId>
    <artifactId>commons-collections</artifactId>
</dependency>
```
#### 主要功能类
1. 配置类
```java
@ConfigurationProperties(prefix = "spring.social.wx")
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
```
2.逻辑类
```java
package com.lyf.springboot.wx;

import com.lyf.springboot.wx.auto.WeChatProperties;
import com.lyf.springboot.wx.data.WeChatAuthResponse;
import com.lyf.springboot.wx.data.WeChatUserInfo;
import com.lyf.springboot.wx.utils.HttpClientUtil;
import com.lyf.springboot.wx.utils.JsonHelper;
import com.lyf.springboot.wx.utils.WxConstants;
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
		if(StringUtils.isEmpty(code) || "null".equalsIg(code)){
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
```
3. 自动注入类
```java
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
```
4.配置自动注入类，在resources/META-INF/目录下创建spring.factories文件
    
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.lyf.springboot.wx.auto.WxAutoConfiguration
```

5. 对WxProperties类的属性进行配置，在resources/META-INF/目录下创建spring-configuration-metadata.json文件
```javascript
{
  "properties": [
    {
      "name": "spring.social.wx.app-id",
      "type": "java.lang.String",
      "sourceType": "com.lyf.springboot.wx.auto.WeChatProperties"
    },
    {
      "name": "spring.social.wx.app-secret",
      "type": "java.lang.String",
      "sourceType": "com.lyf.springboot.wx.auto.WeChatProperties"
    },
    {
      "name": "spring.social.wx.app-name",
      "type": "java.lang.String",
      "sourceType": "com.lyf.springboot.wx.auto.WeChatProperties"
    },
    {
      "name": "spring.social.wx.device-type",
      "type": "java.lang.String",
      "description": "wx platform type, 1 for app, 2 for web",
      "defaultValue": 1,
      "sourceType": "com.lyf.springboot.wx.auto.WeChatProperties"
    }
  ],
  "hints": []
}
```
### starter的使用
> == 0.0.2中更新了spring boot为2.3.1，如果需要在自己项目中使用，建议根据自己的springboot版本进行调整==
1. maven中引入starter-feign
```xml
<!-- 通过openfeign的方式进行请求，可以根据自己的需求，启用okhttp，配置连接池等 -->
<dependency>
    <groupId>com.lyf.springboot.wechat</groupId>
    <artifactId>starter-feign</artifactId>
    <version>0.0.2-SNAPSHOT</version>
</dependency>

<!-- 通过封装httpclient的方式进行请求 -->
<!--
	<dependency>
    <groupId>com.lyf.springboot.wechat</groupId>
    <artifactId>starter-httpclient</artifactId>
    <version>0.0.2-SNAPSHOT</version>
  </dependency>
-->
```
2. 在application.yml中配置自己的微信App信息
```yml
spring:
  social:
    wx:
      app-id: # 配置自己申请的appId
      app-secret: #配置为自己申请的appSecret
      app-name: #此处是为了区分多个app进行的配置 可选
      device-type: 2
```
3. 在自己的controller里面调用wechat-starter的逻辑类，实现授权登陆（获取用户信息）
