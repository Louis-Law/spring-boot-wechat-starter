package com.lyf.springboot.wx.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by 永锋 on 2017/9/28.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lyf.springboot"})
public class WxDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(WxDemoApplication.class, args);
	}
}
