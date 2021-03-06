package com.study.food.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.food.model.Food;
import com.study.food.vo.FoodVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoodMapper extends BaseMapper<Food> {

    List<FoodVo> checkType(@Param("type") Integer type);

    @Update("update food_name set count=count+1 where id=#{id}")
    int updateCount(@Param("id") Integer id);
}
