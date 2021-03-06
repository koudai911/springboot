package com.study.cache.config;


import com.study.cache.cacheable.CacheMessageListener;
import com.study.cache.cacheable.CodeFocusCacheManager;
import com.study.cache.handler.RedisHandler;
import com.study.cache.intereptor.GlobalLimitInterceptor;
import com.study.cache.intereptor.RequestLimitInterceptor;
import com.study.cache.properties.CodeFocusRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author jackl
 * @since 1.0
 */
@Slf4j
@EnableCaching  //开启缓存
@ComponentScan("com.study.cache")
@Configuration
@EnableConfigurationProperties(CodeFocusRedisProperties.class)
public class CodeFocusRedisConfig extends CachingConfigurerSupport implements WebMvcConfigurer {


    @Autowired
    CodeFocusRedisProperties codeFocusRedisProperties;

    @Bean
    public RedisTemplate<String, Serializable> limitRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


    @Bean
    RedisHandler redisHandler(LettuceConnectionFactory redisConnectionFactory) {
        RedisHandler redisHandler = new RedisHandler();
        redisHandler.setKeySerializer(new StringRedisSerializer());
        redisHandler.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisHandler.setHashKeySerializer(new StringRedisSerializer());
        redisHandler.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisHandler.setConnectionFactory(redisConnectionFactory);
        return redisHandler;
    }

    @Bean
    RequestLimitInterceptor requestLimitInterceptor() {
        return new RequestLimitInterceptor();
    }

    /**
     * 生成key的策略【自定义第三种】
     * 使用范围：仅适用于选取第一个参数做键的情况
     * 由于reposotory上不能直接使用spel表达式作key，故而采用key的生成策略的方式来替换
     * <p>
     * 使用时在注解@Cacheable(value = "cacheCheck",keyGenerator = "firstParamKeyGenerator")中指定
     * keyGenerator 下面是全名
     *
     * @return
     */
    @Bean(name = "firstParamKeyGenerator")
    public KeyGenerator firstParamKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(params[0].toString());
                return sb.toString();
            }
        };
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                if (obj != null) {
                    sb.append(obj.toString().hashCode());
                }
            }
            return sb.toString();
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalLimitInterceptor());
        registry.addInterceptor(requestLimitInterceptor());
    }

    @Bean
    GlobalLimitInterceptor globalLimitInterceptor() {
        return new GlobalLimitInterceptor();
    }


    @Bean
    public CodeFocusCacheManager redisCaffeineCacheManager(LettuceConnectionFactory redisConnectionFactory) {
        return new CodeFocusCacheManager(codeFocusRedisProperties, redisHandler(redisConnectionFactory));
    }


    /**
     * @return org.springframework.data.redis.listener.RedisMessageListenerContainer
     * @Description //TODO  redis 消息监听
     * @Param [redisHandler, codeFocusCacheManager]
     **/
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisHandler redisHandler,
                                                                       CodeFocusCacheManager codeFocusCacheManager) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisHandler.getConnectionFactory());
        CacheMessageListener cacheMessageListener = new CacheMessageListener(codeFocusCacheManager);
        //订阅一个信道
        redisMessageListenerContainer.addMessageListener(cacheMessageListener, new ChannelTopic(codeFocusRedisProperties.getCacheConfig().getCacheBaseName()));
        //这个container 可以添加多个 messageListener
        return redisMessageListenerContainer;
    }


}
