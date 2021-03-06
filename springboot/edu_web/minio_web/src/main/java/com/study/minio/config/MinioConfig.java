package com.study.minio.config;

import com.study.minio.properties.MinioProperties;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: minio 配置类
 * @Author: luoshangcai
 * @Date 2020-07-02 17:04
 **/

@Slf4j
@Configuration
public class MinioConfig {

    @Autowired
    private MinioProperties minioProperties;

    /**
     * @Description //TODO 初始化minio客户端
     * @Date 17:08 2020-07-02
     **/
    @Bean
    public MinioClient minioClient() {
        try {
            return new MinioClient(minioProperties.getUrl(), minioProperties.getPort(), minioProperties.getAccessKey(), minioProperties.getSecretKey());
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        }
        return null;
    }

}
