package com.study.cache.service.serviceImpl;

import com.study.cache.controller.TestController;
import com.study.cache.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TestServiceImpls implements TestService {

    @Override
    public List<String> cacheCheck(String id) {
        log.info("进去了service层 模拟调用了db数据层");
        TestController.list.add(id);
        return TestController.list;
    }
}
