package com.study.minio.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: minio 配置参数
 * @Author: luoshangcai
 * @Date 2020-07-02 15:54
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     * minio地址
     */
    private String url;

    /**
     * 端口
     **/
    private Integer port;

    /**
     * minio用户名
     */
    private String accessKey;

    /**
     * minio密码
     */
    private String secretKey;

    /**
     * 文件桶的名称
     */
    private String bucketName;


}
