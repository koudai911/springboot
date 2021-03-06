package com.study.minio.feignapi.fallback;

import com.study.base.common.ResultMsg;
import com.study.minio.feignapi.ElasticjobFeign;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2020-08-22 13:55
 **/
@Component
public class ElasticjobFallBack implements ElasticjobFeign {

    @Override
    public ResultMsg add(Integer id) {
        return ResultMsg.createByErrorMessage("elasticjob 服务异常");
    }
}
