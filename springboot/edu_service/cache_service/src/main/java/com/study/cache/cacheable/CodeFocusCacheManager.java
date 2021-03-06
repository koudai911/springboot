package com.study.cache.cacheable;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.study.cache.handler.RedisHandler;
import com.study.cache.properties.CodeFocusRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.redis.core.TimeoutUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author luoshangcai
 * @Description //TODO 本地一级缓存
 * @Date 10:28 2020-06-29
 * @Param
 * @return
 **/
@Slf4j
public class CodeFocusCacheManager implements CacheManager {

    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    private CodeFocusRedisProperties codeFocusRedisProperties;


    private boolean dynamic = true;

    RedisHandler redisHandler;


    public CodeFocusCacheManager(CodeFocusRedisProperties codeFocusRedisProperties, RedisHandler redisHandler
    ) {
        super();
        this.codeFocusRedisProperties = codeFocusRedisProperties;
        this.dynamic = codeFocusRedisProperties.getCacheConfig().isDynamic();
        this.redisHandler = redisHandler;
    }


    /**
     * @param name key#100s/m/h/d
     * @return
     */
    @Override
    public Cache getCache(String name) {
        long expiration = 0;
        String splitCode = codeFocusRedisProperties.getCacheConfig().getSplitCode();
        log.debug("splitCode:{}", splitCode);
        if (name.contains(splitCode)) {
            String[] split = name.split(splitCode);
            if (split.length > 1) {
                try {
                    String value = split[1];
                    log.debug("value:{}", value);
                    String s = value.replaceAll("[^0-9]", "");
                    log.debug("expirationStr:{}", s);
                    expiration = Integer.parseInt(s);
                    String unitStr = value.replaceAll("[^a-zA-z]", "");
                    log.debug("getCache unistr:{};expiration:{};value:{}", unitStr, expiration, value);
                    TimeUnit unit = TimeUnit.SECONDS;
                    if (!StringUtils.isEmpty(unitStr)) {
                        unitStr = unitStr.toLowerCase();
                        if (unitStr.equals("s")) {
                            unit = TimeUnit.SECONDS;
                        } else if (unitStr.equals("m")) {
                            unit = TimeUnit.MINUTES;
                        } else if (unitStr.equals("h")) {
                            unit = TimeUnit.HOURS;
                        } else if (unitStr.equals("d")) {
                            unit = TimeUnit.DAYS;
                        }
                    }
                    expiration = TimeoutUtils.toSeconds(expiration, unit);
                    log.debug("getCache unistr:{};expiration:{};value:{}", unitStr, expiration, value);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }
        } else {
            expiration = TimeoutUtils.toSeconds(1, TimeUnit.DAYS);
            log.debug("getCache no split name:{};expiration:{}", name, expiration);
        }
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        cache = new CodeFocusCache(name,
                caffeineCache(name, expiration), codeFocusRedisProperties, expiration, redisHandler);
        log.debug("getCache name:{};expiration:{}", name, expiration);
        // 如果有旧数据就 返回旧数据 不会再新增
        // 如果没有  就新增
        Cache oldCache = cacheMap.putIfAbsent(name, cache);
        return oldCache == null ? cache : oldCache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    public CaffeineCache caffeineCache(String name, long expiration) {
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
        if (expiration > 0) {
            // 设置最后一次写入或访问后经过固定时间过期
            cacheBuilder.expireAfterWrite(expiration, TimeUnit.SECONDS);
        }
        int initialCapacity = codeFocusRedisProperties.getCacheConfig().getCaffeine().getInitialCapacity();
        if (initialCapacity > 0) {
            // 初始的缓存空间大小
            cacheBuilder.initialCapacity(initialCapacity);
        }
        long maximumSize = codeFocusRedisProperties.getCacheConfig().getCaffeine().getMaximumSize();
        if (maximumSize > 0) {
            // 缓存的最大条数
            cacheBuilder.maximumSize(maximumSize);
        }
        return new CaffeineCache(name, cacheBuilder.build());
    }

    public void clearLocal(String cacheName, Object key) {
        Cache cache = cacheMap.get(cacheName);
        if (cache == null) {
            return;
        }

        CodeFocusCache codeFocusCache = (CodeFocusCache) cache;
        codeFocusCache.clearLocal(key);
    }

}
