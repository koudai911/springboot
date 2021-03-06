package com.study.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author luoshangcai
 * @Description //TODO minio 分布式文件存储服务
 * @Date 15:23 2020-07-03
 * @Param
 * @return
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class MinioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinioApplication.class, args);
    }

}
