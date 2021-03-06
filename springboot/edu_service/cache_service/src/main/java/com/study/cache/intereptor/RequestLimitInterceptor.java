package com.study.cache.intereptor;

import com.study.base.util.IpUtils;
import com.study.cache.annotation.RequestLimit;
import com.study.cache.annotation.RequestLimitType;
import com.study.cache.handler.RedisHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author jackl
 * @since 1.0
 */
@Slf4j
public class RequestLimitInterceptor extends HandlerInterceptorAdapter {

    private final String PREFIX_REQUEST_LIMIT = "request.limit";

    @Autowired
    private RedisHandler redisHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequestLimit requestLimit = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), RequestLimit.class);
            if (requestLimit == null) {
                requestLimit = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), RequestLimit.class);
            }

            String ip = IpUtils.getIpAddrExt(request);
            String methodName = handlerMethod.getMethod().getName();

            if (requestLimit != null && StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(methodName)) {

                String cacheKey = PREFIX_REQUEST_LIMIT + ":" + methodName + ":" + ip;

                setLid(requestLimit.userId(), requestLimit.userIdRequestLimitType(), request, cacheKey);

                setLid(requestLimit.clientId(), requestLimit.clientIdRequestLimitType(), request, cacheKey);

                Object cacheValue = redisHandler.find(cacheKey);
                int limit = requestLimit.limit();
                int period = requestLimit.period();
                TimeUnit unit = requestLimit.unit();
                if (cacheValue != null && StringUtils.isNotBlank(cacheValue.toString())) {
                    Long countNum = NumberUtils.toLong(cacheValue.toString());
                    if (countNum >= limit) {
                        redisHandler.increment(cacheKey, period, unit);
                        throw new Exception("接口访问频繁,稍后重试");
                    }
                }
                redisHandler.increment(cacheKey, period, unit);
            }
        }
        return true;
    }

    private void setLid(String lid, RequestLimitType requestLimitType, HttpServletRequest request, String cacheKey) {
        if (!StringUtils.isEmpty(lid)) {
            switch (requestLimitType) {
                case REQUEST:
                    Map<String, Object> paraMap = WebUtils.getParametersStartingWith(request, "");
                    lid = MapUtils.getString(paraMap, lid);
                    cacheKey = cacheKey + ":" + lid;
                    break;
                case HEADER:
                    lid = request.getHeader(lid);
                    cacheKey = cacheKey + ":" + lid;
                    break;
            }
        }
    }


}
