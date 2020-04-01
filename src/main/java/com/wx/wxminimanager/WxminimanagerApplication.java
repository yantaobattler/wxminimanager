package com.wx.wxminimanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wx.wxminimanager.user.dao")
public class WxminimanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxminimanagerApplication.class, args);
	}

}
