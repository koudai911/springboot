package com.study.seata.feignapi.fallback;

import com.study.base.common.ResultMsg;
import com.study.seata.feignapi.MinioFeign;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2020-08-22 13:55
 **/
@Component
public class MinioFallBack implements MinioFeign {

    @Override
    public ResultMsg add(Integer id, Integer number) {
        return ResultMsg.createByErrorMessage("minio 服务异常");
    }
}
