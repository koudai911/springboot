package com.study.verifydemo.model;

import lombok.Data;


/**
 * @Author luoshangcai
 * @Description //TODO   用户实体类
 * @Date 16:28 2020-05-20
 * @Param 
 * @return 
 **/
@Data
public class User {

    /**
     * 昵称
     */
    private String name;
    /**
     * 密码
     */
    private String password;

    public User(){
        super();
    }
    public User(String name,String password){
        this.name=name;
        this.password=password;
    }
}
