package com.study.verifydemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author luoshangcai
 * @Description //TODO   用户实体类
 * @Date 16:28 2020-05-20
 * @Param 
 * @return 
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 昵称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
}
