package com.study.seata.feignapi;

import com.study.base.common.ResultMsg;
import com.study.seata.feignapi.fallback.MinioFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "minio",fallback = MinioFallBack.class)
public interface MinioFeign {

    @RequestMapping("/order/add")
    ResultMsg add(@RequestParam("id") Integer id, @RequestParam("number") Integer number);
}
