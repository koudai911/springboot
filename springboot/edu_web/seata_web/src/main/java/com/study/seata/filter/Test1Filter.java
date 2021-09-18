package com.study.seata.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description:
 * @Author: luoshangcai
 * @Date 2021-08-28 18:15
 **/
public class Test1Filter implements Filter {

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain filterChain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request=(HttpServletRequest)arg0;
        System.out.println("自定义过滤器filter1触发,拦截url:"+request.getRequestURI());
        filterChain.doFilter(arg0,arg1);
    }
}
