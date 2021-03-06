package com.study.elasticjob.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/xx")
public class XxController {

//    @Autowired
//    private TestService testService;

    /**
     * @Description //TODO  分布式方法锁
     *                  方法限流 限制  2秒只能调用2000次 设置的值应小于服务限流
     * @Param []
     * @return java.lang.String
     **/
//    @RequestMapping("/2222test")
//    @DistributedLock(lock= LockType.METHOD,timeOut = 15000)
//    @RequestLimit(limit = 180,period =1 )
//    public String test(String id){
//        List<String> ids = testService.cacheCheck(id);
//        return JSON.toJSONString(ids);
//    }
}
