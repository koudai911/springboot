package com.study.zipkin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author luoshangcai
 * @Description //TODO 服务一
 * @Date 17:33 2020-06-30
 * @Param
 * @return
 **/
@RestController
@RequestMapping("server1")
public class ZipkinBraveController1 {


    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Description: 第一步调用
     * @Param:
     * @return: 字符串
     * @Author: Mr.Yang
     */
    @RequestMapping("/zipkin")
    public String service1() {
        try {
            Thread.sleep(100);
            String PUSH_URL = "http://localhost:6040/zipkin-server/server2/zipkin";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.add("dataJson", dataJson);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);
            return restTemplate.postForObject(PUSH_URL, entity, String.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
