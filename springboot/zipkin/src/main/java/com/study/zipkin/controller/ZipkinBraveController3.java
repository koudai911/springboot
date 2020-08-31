package com.study.zipkin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author luoshangcai
 * @Description //TODO 服务3
 * @Date 17:33 2020-06-30
 * @Param
 * @return
 **/
@RestController
@RequestMapping("server3")
public class ZipkinBraveController3 {
    /**
     * @Description: 第三步调用
     * @Param:
     * @return:  字符串
     */
    @RequestMapping("/zipkin")
    public String service1() throws Exception {
        Thread.sleep(200);
        return "你好,欢迎进入Zipkin的世界";
    }

}
