package com.study.verifydemo.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
public @interface ApiIdempotent {
}
