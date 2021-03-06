package com.study.seata.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.study.base.annotation.DateValidator;
import com.study.base.group.insert;
import com.study.base.group.update;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Description: 用户
 * @Author: luoshangcai
 * @Date 2020-08-15 15:28
 **/
@Data
@TableName("user")
public class User extends Model<User> {
    @NotNull(message = "id不能为null", groups = {insert.class})
    @TableId
    private Integer id;

    @Length(min = 1, max = 100, message = "用户名不能为空", groups = {insert.class})
    @TableField("name")
    private String name;

    @Min(value = 0, message = "年龄最小值不能小于0", groups = {update.class})
    @Max(value = 150, message = "年龄最大值不能大于150", groups = {update.class})
    @TableField("age")
    private int age;

    @DateValidator(dateFormat = "yyyy-MM-dd", groups = {insert.class})
    @NotEmpty(message = "生日不能为null", groups = {insert.class})
    @TableField("birthday")
    private String birthday;
}
