package com.study.elasticjob.model;

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
@TableName("t_test")
@NoArgsConstructor
@AllArgsConstructor
public class Ttest extends Model<Ttest> {

    private Integer id;
}
