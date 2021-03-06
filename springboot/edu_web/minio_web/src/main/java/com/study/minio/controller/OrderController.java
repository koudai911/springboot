package com.study.minio.controller;

import com.alibaba.fastjson.JSON;
import com.study.base.common.ResultMsg;
import com.study.base.common.ValidateResult;
import com.study.base.group.insert;
import com.study.base.util.ValidateUtil;
import com.study.minio.feignapi.ElasticjobFeign;
import com.study.minio.mapper.OrderMapper;
import com.study.minio.model.Order;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户controller
 * @Author: luoshangcai
 * @Date 2020-08-15 17:10
 **/
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ElasticjobFeign elasticjobFeign;

    @RequestMapping("/add")
    @GlobalTransactional(name = "useradd", rollbackFor = RuntimeException.class)
    public ResultMsg add(@RequestParam("id") Integer id, @RequestParam("number") Integer number) {
        Order order = new Order(id, number);
        ValidateResult validateResult = ValidateUtil.validateBean(order, insert.class);
        if (validateResult.hasErrors()) {
            return ResultMsg.createByErrorMessage(JSON.toJSONString(validateResult.getAllErrors()));
        }
        try {
            orderMapper.insert(order);
            ResultMsg add = elasticjobFeign.add(id);
            if (200 != add.getCode()) {
                throw new RuntimeException(add.getMsg());
            }
            return ResultMsg.createBySuccess();
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error("minio添加数据异常:{}", e);
            throw new RuntimeException(e);
        }
    }
}
