package com.study.canal.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = RedisProperties.PROPERTIES_FILE)
@Data
public class RedisProperties {
    public static final String PROPERTIES_FILE = "spring.redis";

    private Integer database;
    private String host;
    private Integer port;
    private Boolean ssl;
    private String password;
    private Long timeout;
    private Integer maxActive;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxWait;
}
