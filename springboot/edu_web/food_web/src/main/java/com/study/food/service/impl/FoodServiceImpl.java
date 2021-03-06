package com.study.food.service.impl;

import com.study.base.common.ResultMsg;
import com.study.base.common.ResultStatusCode;
import com.study.food.mapper.FoodMapper;
import com.study.food.service.FoodService;
import com.study.food.vo.FoodVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Random;

import java.util.List;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2020-09-06 21:32
 **/
@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    private static Random random = new Random();

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public ResultMsg checkType(Integer type) {
        if (null == type) {
            return ResultMsg.createByErrorResultStatusCode(ResultStatusCode.INVALID_PARAM, null);
        }
        List<FoodVo> list = foodMapper.checkType(type);
        //随机获取集合中一个值
        if (CollectionUtils.isEmpty(list)) {
            return ResultMsg.createByErrorResultStatusCode(ResultStatusCode.CHECK_NO_DATA);
        }
        FoodVo vo = list.get(random.nextInt(list.size()));
        return ResultMsg.createBySuccess(vo);
    }
}
