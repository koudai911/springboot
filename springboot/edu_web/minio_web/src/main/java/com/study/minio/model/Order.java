package com.study.minio.model;

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
@TableName("t_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Model<Order> {

    private Integer id;

    private Integer number;

}
