package com.study.cache.cacheable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.cache.handler.RedisHandler;
import com.study.cache.properties.CodeFocusRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author  jackl
 * @since 1.0
 */
@Slf4j
public class CodeFocusCache extends AbstractValueAdaptingCache {

	private String name;

	RedisHandler redisHandler;

	private CaffeineCache caffeineCache;

	private String cachePrefix;

	private long expiration = 100;

	private String topic;

	private Map<Object, ReentrantLock> keyLockMap = new ConcurrentHashMap<Object, ReentrantLock>();


	CodeFocusRedisProperties codeFocusRedisProperties;

	protected CodeFocusCache(boolean allowNullValues) {
		super(allowNullValues);
	}


	public CodeFocusCache(String name, CaffeineCache caffeineCache, CodeFocusRedisProperties codeFocusRedisProperties, long expiration,
						  RedisHandler redisHandler) {

		super(codeFocusRedisProperties.getCacheConfig().isCacheNullValues());
		this.name = name;
		this.caffeineCache = caffeineCache;
		this.cachePrefix = codeFocusRedisProperties.getCacheConfig().getCacheBaseName();
		this.expiration = expiration;
		this.topic=codeFocusRedisProperties.getCacheConfig().getCacheBaseName();
		this.redisHandler=redisHandler;
		this.codeFocusRedisProperties=codeFocusRedisProperties;
	}


	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		return this;
	}

	public long getExpiration(){
		return this.expiration;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		log.debug("get;key：{}",key);
		Object value = lookup(key);
		if(value != null) {
			return (T) value;
		}
		ReentrantLock lock = keyLockMap.get(key);
		if(lock == null) {
			lock = new ReentrantLock();
			keyLockMap.putIfAbsent(key, lock);
		}
		try {
			lock.lock();
			value = lookup(key);
			if(value != null) {
				return (T) value;
			}
			value = valueLoader.call();
			Object storeValue = toStoreValue(value);
			put(key, storeValue);
			return (T) value;
		} catch (Exception e) {
			throw new ValueRetrievalException(key, valueLoader, e.getCause());
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void put(Object key, Object value) {
		log.debug("put; key:{};value:{}", key,value);
		if (!super.isAllowNullValues() && value == null) {
			this.evict(key);
            return;
        }
		Object dataKey = getKey(key);
		Object dataValue = toStoreValue(value);
		//更新redis 缓存数据
		addCache(this.name,dataKey,dataKey,dataValue);
		//当redis 缓存数据变更时 及时发布订阅通知其他节点更新缓存
		push(this.name, getKey(key));
		// 再更新本地缓存数据
		log.info("更新本地缓存value:{};key:{}",dataValue,dataKey);
		caffeineCache.put(dataKey, toStoreValue(value));
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		log.debug("putIfAbsent; key:{},value:{}",key,value);
		Object cacheKey = getKey(key);
		Object prevValue = null;
		// 考虑使用分布式锁，或者将redis的setIfAbsent改为原子性操作
		synchronized (key) {
			prevValue=findRedisCache(cacheKey);
			if(prevValue == null) {
				Object dataKey = getKey(key);
				Object dataValue = toStoreValue(value);
				addCache(this.name,dataKey,dataKey,dataValue);
				push(this.name, dataKey);
				caffeineCache.put(cacheKey, toStoreValue(value));
			}
		}
		return toValueWrapper(prevValue);
	}


	@Override
	public void evict(Object key) {
		log.debug("evict; key:{}",key);
		// 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
		Object dataKey = getKey(key);
		removeRedisCache(dataKey);
		push(this.name, dataKey);
		caffeineCache.evict(key);
	}

	@Override
	public void clear() {
		log.debug("clear; name:{}",name);
		push(this.name, null);
		caffeineCache.clear();
		clearRedisData(name);
	}

	@Override
	protected Object lookup(Object key) {
		log.debug(" lookup; key:{};name:{}",key,name);
		Object value=null;
		Object cacheKey = getKey(key);
		try{
			value = caffeineCache.get(cacheKey);
			if(( value instanceof SimpleValueWrapper)){
				value=((SimpleValueWrapper) value).get();
			}
			if(value != null) {
				return value;
			}
		}catch (Exception e){
			log.error(e.getMessage());
		}
		value=findRedisCache(cacheKey);
		log.debug("lookup; cacheKey:{},value:{};expiration:{}",cacheKey,value,expiration);
		if(value != null) {
			caffeineCache.put(cacheKey, value);
		}
		return value;
	}

	/**
	 * 缓存变更时通知其他节点清理本地缓存
	 */
	private void push(String name, Object dataKey) {
		CacheMessage cacheMessage=new CacheMessage();
		cacheMessage.setKey(dataKey);
		cacheMessage.setCacheName(name);

		ObjectMapper objectMapper=new ObjectMapper();
		String str= null;
		try {
			str = objectMapper.writeValueAsString(cacheMessage);

			redisHandler.convertAndSend(topic,str);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		}


	/**
	 * 清理本地缓存
	 * @param key
	 */
	public void clearLocal(Object key) {
		log.debug("clearLocal  key:{};caffeineCache:{}", key,caffeineCache);
		if(key == null) {
			caffeineCache.clear();
		} else {
			caffeineCache.evict(key);
		}
	}


	private Object getKey(Object key) {
		String soltKey = name.split(codeFocusRedisProperties.getCacheConfig().getSplitCode())[0];
		String concat = "{"+soltKey+"}"+cachePrefix.concat(":").concat(name + ":").concat(key.toString()).concat(":");
		log.debug("getKey concat:{}",concat);
		return concat;
	}

	private String getZsetKey(String name){
		String soltKey = name.split(codeFocusRedisProperties.getCacheConfig().getSplitCode())[0];
		String concat =  "{"+soltKey+"}"+cachePrefix.concat(":").concat(soltKey).concat(":");
		log.debug("getZsetKey concat:{},name:{},splitCode:{}",concat,soltKey,codeFocusRedisProperties.getCacheConfig().getSplitCode());
		return concat;
	}

	/**
	 * 添加缓存redis
	 * @param key
	 * @param value
	 * @param dataKey
	 * @param dataValue
	 */
	public void addCache(String key, Object value, Object dataKey, Object dataValue) {
		StringBuilder sbu=new StringBuilder();
		sbu.append("redis.call('set',KEYS[1],ARGV[1]);");
		sbu.append("redis.call('expire', KEYS[1], ARGV[2]);");
		String zsetKey = getZsetKey(key);
		sbu.append("redis.call('lpush',KEYS[2],ARGV[3]);");
		sbu.append("local c = redis.call('ttl',KEYS[2])");
		sbu.append("\n if tonumber(c) > tonumber(ARGV[4]) then");
		sbu.append("\n else ");
		sbu.append("  redis.call('expire', KEYS[2], ARGV[4])");
		sbu.append("\n end ");
		DefaultRedisScript LOCK_LUA_SCRIPT = new DefaultRedisScript<>(sbu.toString());
		List<Object> keys =new ArrayList<>();
		keys.add(dataKey);
		keys.add(zsetKey);
		log.debug("addCache  zsetKey:{},value:{},dataKey:{},dataValue:{},expiration:{}",zsetKey,value,dataKey,dataValue,expiration);
		redisHandler.execute(LOCK_LUA_SCRIPT,keys,dataValue,expiration,value,expiration);

	}

	/**
	 * 清空redis
	 * @param key
	 */
	public void clearRedisData(String key){
		log.debug("clearRedisData key:{}",key);
		String zsetKey = getZsetKey(key);
		List dataKey = redisHandler.opsForList().range(zsetKey, 0, -1);
		int index=1;
		List<Object> keys =new ArrayList<>();
		StringBuilder sbu=new StringBuilder();
		String delKeys="";
		for(int i=0;i<dataKey.size();i++){
			String delKey = "KEYS[" + index + "]";
			if(StringUtils.isEmpty(delKeys)){
				delKeys= delKey;
			}else{
				delKeys=delKeys+","+delKey;
			}
			index++;
			keys.add(dataKey.get(i));
		}
		if(!StringUtils.isEmpty(delKeys)){
			sbu.append("redis.call('del',"+delKeys+");");
		}
		sbu.append("redis.call('ltrim',KEYS["+(index)+"],1,0);");
		DefaultRedisScript LOCK_LUA_SCRIPT = new DefaultRedisScript<>(sbu.toString());
		keys.add(zsetKey);
		log.debug("clearRedisData keys:{},zsetKey:{}",keys.size(),sbu.toString());
		redisHandler.execute(LOCK_LUA_SCRIPT,keys);
	}

	public Object findRedisCache(Object cacheKey){
		log.info("查询redis了");
		return redisHandler.find(cacheKey);
	}

	public void removeRedisCache(Object dataKey){
		log.debug("removeRedisCache dataKey:{}",dataKey);
		redisHandler.remove(dataKey);

		String zsetKey = getZsetKey(this.name.split(codeFocusRedisProperties.getCacheConfig().getSplitCode())[0]);
		log.debug("removeRedisCache zsetKey:{},dataKey:{}",zsetKey,dataKey);
		redisHandler.opsForList().remove(zsetKey,0,dataKey);
	}

}
