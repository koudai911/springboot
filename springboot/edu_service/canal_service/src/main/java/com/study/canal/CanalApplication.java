package com.study.canal;

import com.study.canal.utils.CanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * canal实现 mysql的binlog日志实时同步到redis中
 */
@SpringBootApplication
public class CanalApplication {

    public static void main(String[] args) {

        SpringApplication.run(CanalApplication.class, args);
        CanalClient.run();
    }

}
