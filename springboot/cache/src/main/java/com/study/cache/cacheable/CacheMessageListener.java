package com.study.cache.cacheable;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;


/**
 * @Author luoshangcai
 * @Description //TODO reids二级缓存
 * 				redis订阅收到的信息
 * @Date 10:53 2020-06-29
 * @Param 
 * @return 
 **/
@Slf4j
public class CacheMessageListener implements MessageListener {

	private CodeFocusCacheManager codeFocusCacheManager;

	public CacheMessageListener(CodeFocusCacheManager codeFocusCacheManager) {
		super();
		this.codeFocusCacheManager = codeFocusCacheManager;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] body = message.getBody();//请使用valueSerializer
		String itemValue = new String(body);
		try {
			ObjectMapper objectMapper=new ObjectMapper();
			CacheMessage cacheMessage = objectMapper.readValue(itemValue ,CacheMessage.class);
			log.debug("onMessage:{};cacheName:{}",itemValue,cacheMessage.getCacheName());
			// 清除本地缓存
			codeFocusCacheManager.clearLocal(cacheMessage.getCacheName(), cacheMessage.getKey());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}


}
