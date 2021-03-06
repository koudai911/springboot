package com.study.verifydemo.annotation;

import java.lang.annotation.*;

/**
 * @Author luoshangcai
 * @Description //TODO  在需要保证  接口幂等性
 *             使用： 在controller的方法上使用注解
 * @Date 9:39 2020-05-21
 * @Param
 * @return
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiIdempotent {
    /**
     * 是否创建新的token
     */
    boolean generateToken() default false;
    /**
     * 是否移除token
     */
    boolean removeToken() default false;
}
