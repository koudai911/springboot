package com.study.verifydemo.filter;

import com.alibaba.fastjson.JSON;
import com.study.verifydemo.common.ResultMsg;
import com.study.verifydemo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author luoshangcai
 * @Description //TODO 幂等性接口拦截器
 * @Date 10:39 2020-05-21
 * @Param 
 * @return 
 **/
@WebFilter(filterName = "apiIdempotentInterceptor", urlPatterns = { "/*" })
public class ApiIdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("token");
        String name = request.getHeader("name");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
        if (null!=methodAnnotation ) {
            ResultMsg resultMsg = checkApiIdempotent(token, name);
            // 幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
            if(200 !=resultMsg.getCode()){
                //token无效
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(401);
                response.getWriter().write(JSON.toJSONString(resultMsg));
                return false;
            }
        }

        return true;
    }

    private ResultMsg checkApiIdempotent(String token,String name) {
        return tokenService.checkToken(token, name);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}