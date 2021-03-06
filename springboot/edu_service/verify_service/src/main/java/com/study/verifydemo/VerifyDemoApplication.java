package com.study.verifydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Author luoshangcai
 * @Description //TODO
 * 1、redis+token 实现接口幂等性
 * 2、redis分布式锁+切面实现接口幂等性问题
 * @Date 10:06 2020-06-23
 * @Param
 * @return
 **/
@SpringBootApplication
public class VerifyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerifyDemoApplication.class, args);
	}

}
