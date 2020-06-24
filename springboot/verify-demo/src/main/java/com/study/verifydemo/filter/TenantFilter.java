package com.study.verifydemo.filter;


import com.study.verifydemo.common.ResultMsg;
import com.study.verifydemo.utils.ThreadLocalMapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author luoshangcai
 * @Description //TODO tenant 租户过滤器
 * @Date 10:59 2020-06-23
 * @Param
 * @return
 **/
@Component
@WebFilter(filterName = "tenantFilter",urlPatterns = "/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter implements Filter {

    public  final static String TENANT_KEY="tenantId";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;

        String method =  request.getMethod();
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return;
        }

        String schema = request.getHeader(TENANT_KEY);

        // get请求如果头部没有租户id,从url参数中取
        if(StringUtils.equalsIgnoreCase(method, HttpMethod.GET.name()) && StringUtils.isBlank(schema)){
            schema = request.getParameter(TENANT_KEY);
        }

        if (StringUtils.isBlank(schema)) {
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setStatus(401);
            response.getWriter().write(ResultMsg.createByErrorMessage("无法识别的schema").toString());
            return;
        }

        ThreadLocalMapUtils.put(TENANT_KEY,schema);
         chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
