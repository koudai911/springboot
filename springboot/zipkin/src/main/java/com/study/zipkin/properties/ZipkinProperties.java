package com.study.zipkin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author luoshangcai
 * @Description //TODO zipkin 配置
 * @Date 16:54 2020-06-30
 * @Param
 * @return
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = ZipkinProperties.ZIPKIN_PREFIX)
public class ZipkinProperties {

    public final static  String ZIPKIN_PREFIX="zipkin";

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * zipkin地址
     */
    private String url;

    /**
     * 连接时间
     */
    private int connectTimeout;

    /**
     * 读取时间
     */
    private int readTimeout;

    /**
     * 每间隔多少秒执行一次Span信息上传
     */
    private int flushInterval;

    /**
     * 是否启动压缩
     */
    private boolean compressionEnabled;

}
