package com.study.food.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2020-08-22 13:44
 **/

@Data
@TableName("food_type")
@NoArgsConstructor
@AllArgsConstructor
public class FoodType extends Model<FoodType> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    public FoodType(String name) {
        this.name = name;
    }

}
