package com.study.verifydemo.filter;

import com.alibaba.fastjson.JSON;
import com.study.base.common.Constant;
import com.study.base.common.ResultMsg;
import com.study.verifydemo.annotation.ApiIdempotent;
import com.study.verifydemo.service.TokenService;
import com.study.verifydemo.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Author luoshangcai
 * @Description //TODO  基于 redis+ token机制实现接口幂等性问题
 * 幂等性接口拦截器
 * @Date 10:39 2020-05-21
 * @Param 
 * @return 
 **/
@WebFilter(filterName = "apiIdempotentInterceptor", urlPatterns = { "/*" })
@Slf4j
public class ApiIdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("token");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
        if (null!=methodAnnotation ) {
            //  创建 token
            boolean isGenerate = methodAnnotation.generateToken();
            if (isGenerate) {
                String formToken = UUID.randomUUID().toString();
                redisUtils.set(formToken, formToken, Constant.EXPIRE_TIME_MINUTE);
                response.setHeader("formToken",formToken);
                log.info("创建令牌成功，token:" + formToken);
                return true;
            }

            // 删除token令牌
            boolean isRemove = methodAnnotation.removeToken();
            if (isRemove) {
                ResultMsg resultMsg = checkApiIdempotent(token);
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
        }
        return true;
    }

    private ResultMsg checkApiIdempotent(String token) {
        return tokenService.checkToken(token);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}