package com.study.minio.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author luoshangcai
 * @Description //TODO 数据库属性配置  - durid
 * @Date 16:26 2020-08-20
 **/
@Data
@ConfigurationProperties(prefix = DruidDataSourceProperties.DS, ignoreUnknownFields = true, ignoreInvalidFields = true)
public class DruidDataSourceProperties {
    //对应配置文件里的配置键
    public final static String DS="spring.datasource";

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private int initialSize;

    private int minIdle;

    private int maxActive;

    private int maxWait;

    private int timeBetweenEvictionRunsMillis;

    private int minEvictableIdleTimeMillis;

    private String validationQuery;

    private boolean testWhileIdle;

    private boolean testOnBorrow;

    private boolean testOnReturn;

    private boolean poolPreparedStatements;

    private String filters;

}
