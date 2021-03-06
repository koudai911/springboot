package com.study.food.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("food_name")
@NoArgsConstructor
@AllArgsConstructor
public class Food extends Model<Food> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("type_id")
    private Integer typeId;

    private String name;

    private Integer delFlag;
    /**
     * 出现次数
     **/
    private Integer count;

    public Food(Integer typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }

    public Food(Integer id, Integer delFlag) {
        this.id = id;
        this.delFlag = delFlag;
    }
}
