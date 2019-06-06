package com.smart.socket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling//启用定时任务
@EnableCaching//启用缓存
@EnableTransactionManagement//启用数据库事务
@MapperScan(basePackages = "com.smart.socket.mapping")//mybatis接口类加载
@EnableAsync
public class SmartgbApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SmartgbApplication.class, args);
	}
}
