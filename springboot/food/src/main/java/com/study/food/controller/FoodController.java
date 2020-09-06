package com.study.food.controller;

import com.study.base.common.ResultMsg;
import com.study.base.common.ResultStatusCode;
import com.study.food.mapper.FoodMapper;
import com.study.food.mapper.FoodTypeMapper;
import com.study.food.model.Food;
import com.study.food.model.FoodType;
import com.study.food.service.impl.FoodServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 菜名
 * @Author: luoshangcai
 * @Date 2020-09-06 20:12
 **/
@RestController
@Slf4j
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodServiceImpl foodServiceImpl;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private FoodTypeMapper foodTypeMapper;


    @RequestMapping("/checkType")
    public ResultMsg checkType(@RequestParam("type") Integer type){
        if(null ==type) return  ResultMsg.createByErrorResultStatusCode(ResultStatusCode.INVALID_PARAM,null);
        return foodServiceImpl.checkType(type);

    }

    @RequestMapping("/updateOrDelete/count")
    public ResultMsg updateOrDeleteCount(@RequestParam("id") Integer id,@RequestParam("delFlag") Integer delFlag){
        if(null ==id) return  ResultMsg.createByErrorResultStatusCode(ResultStatusCode.INVALID_PARAM,null);
        if(null ==delFlag){
            // 修改 菜名确定次数
            foodMapper.updateCount(id);
        }else{
            // 逻辑删除
            Food food=new Food(id,delFlag);
            foodMapper.updateById(food);
        }
        return ResultMsg.createBySuccess();
    }
}
