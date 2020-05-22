package com.study.canal;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CanalApplicationTests {

    @Test
    void contextLoads() {
        int one = 123456789;
        double two = 123456.789;
        String s = String.format("第一个参数：%,d 第二个参数：%,.3f", one, two);
        System.out.println(s);

    }

}
