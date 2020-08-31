package com.study.base.exception;

/**
 * @Description: 自定义异常处理
 * @Author: luoshangcai
 * @Date 2020-07-02 18:41
 **/
public class CommonException extends RuntimeException {


    public CommonException(String message) {
        super(message);
    }

    public CommonException() {
        super();
    }
}
