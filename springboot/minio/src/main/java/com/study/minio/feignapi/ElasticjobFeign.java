package com.study.minio.feignapi;

import com.study.base.common.ResultMsg;
import com.study.minio.feignapi.fallback.ElasticjobFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "elasticjob",fallback = ElasticjobFallBack.class)
public interface ElasticjobFeign {

    @RequestMapping("/test/add")
    ResultMsg add(@RequestParam("id") Integer id);
}
