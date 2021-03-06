package com.study.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author luoshangcai
 * @Description 分布式锁+方法限流（根据方法名）+服务限流（根据ip）+CaffeineCache（本地缓存为一级缓存）+redis（二级缓存）
 * 流程：先查询CaffeineCache本地 》redis 》db 》redis存储 》redis发布订阅模式 通知其他服务（清除本地存储）》CaffeineCache 本地存储
 * 如果中间流程 redis有存储 就保存到本地 不查db  如果本地有就不查redis 直接查一级缓存
 * @Date 16:45 2020-06-29
 * @Param
 * @return
 **/
@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

}
