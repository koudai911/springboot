package com.study.verifydemo.utils;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author luoshangcai
 * @Description //TODO 本地线程 工具类
 * @Date 10:53 2020-06-23
 * @Param 
 * @return 
 **/
public class ThreadLocalMapUtils {
    /**
     * The constant threadContext.
     */
    private final static TransmittableThreadLocal<Map<String, Object>> THREAD_CONTEXT_MAP = new ExtMapThreadLocal();
    /**
     * 获取ThreadLocal对象
     *
     * @return Map
     */
    private static Map<String, Object> getContextMap() {
        return THREAD_CONTEXT_MAP.get();
    }

    /**
     * 保存对象
     *
     * @param key   key值
     * @param value value值
     */
    public static void put(String key, Object value) {
        getContextMap().put(key, value);
    }

    /**
     * 获取值
     *
     * @param key key值
     * @return
     */
    public static Object get(String key) {
        return getContextMap().get(key);
    }

    /**
     * 根据key删除值
     *
     * @param key key值
     */
    public static void remove(String key) {
        getContextMap().remove(key);
    }

    /**
     * 清除这个线程里面的本地变量
     */
    public static void removeThreadLocal(){
        THREAD_CONTEXT_MAP.remove();
    }

    /**
     * 清除ThreadLocal所有对象
     */
    public static void clear() {
        getContextMap().clear();
    }

    /**
     * 自定义一个ThreadLocal map集合设置初始map默认值为128
     */
    public static class ExtMapThreadLocal extends TransmittableThreadLocal<Map<String, Object>> {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>(128) {

                private static final long serialVersionUID = 7441080537456112150L;

                @Override
                public Object put(String key, Object value) {
                    return super.put(key, value);
                }
            };
        }
    }
}
