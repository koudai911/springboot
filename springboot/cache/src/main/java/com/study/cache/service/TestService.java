package com.study.cache.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "test")
public interface TestService {

    /**
     * @Author luoshangcai
     * 查询信息    并缓存到redis中
     *   # 做了处理 缓存保存多长时间
     * @Param [list]
     * @return java.util.List<java.lang.String>
     **/
    @Cacheable(value = "cacheCheck#300s" ,keyGenerator  = "firstParamKeyGenerator")
    List<String> cacheCheck(String id);
}
