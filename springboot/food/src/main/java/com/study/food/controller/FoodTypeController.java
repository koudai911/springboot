package com.study.food.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.base.common.ResultMsg;
import com.study.food.mapper.FoodMapper;
import com.study.food.mapper.FoodTypeMapper;
import com.study.food.model.FoodType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 菜类型
 * @Author: luoshangcai
 * @Date 2020-09-06 20:12
 **/
@RestController
@Slf4j
@RequestMapping("/foodType")
public class FoodTypeController {

    @Autowired
    private FoodMapper FoodMapper;

    @Autowired
    private FoodTypeMapper foodTypeMapper;

    @RequestMapping("/all")
    public ResultMsg all(){
        List<FoodType> list = foodTypeMapper.selectList(new QueryWrapper<>());
        return ResultMsg.createBySuccess(list);
    }
}
