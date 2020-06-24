package com.study.cache.annotation;

import java.lang.annotation.*;

/**
 * @Author luoshangcai
 * @Description //TODO 分布式锁开关
 * @Date 10:17 2020-06-24
 * @Param 
 * @return 
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * 分布式锁开关:true=开启;false=关闭
     */
    boolean open() default true;

    LockType lock() default LockType.IP;

    String field() default "";

    int expire() default 5000;

    int timeOut() default 3000;
}
/*
 * 使用分布式列子
 * @DistributedLock(lock = LockType.METHOD)
 */
