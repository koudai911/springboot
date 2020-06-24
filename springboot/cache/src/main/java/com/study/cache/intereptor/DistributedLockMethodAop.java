package com.study.cache.intereptor;

import com.study.cache.annotation.DistributedLock;
import com.study.cache.lock.RedisReentrantLock;
import com.study.cache.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author  jackl
 * @since 1.0
 */
@Slf4j
@Aspect
@Component
public class DistributedLockMethodAop {

    private final static Long CONNECTION_TIMEOUT = 3000L;


    @Resource
    private HttpServletRequest request;

    @Autowired
    RedisTemplate<String, Serializable> limitRedisTemplate;


    @Pointcut(value = "@annotation(com.study.cache.annotation.DistributedLock)")
    public void pointcutDistributedLockMethod() {

    }

    @Around("pointcutDistributedLockMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = null;
        DistributedLock distributedLock = null;
        String lockName = null;
        try {
            Signature signature = pjp.getSignature();
            MethodSignature method = (MethodSignature) signature;
            distributedLock = AnnotationUtils.findAnnotation(method.getMethod(), DistributedLock.class);
        } catch (Exception e) {

        }
        final RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        final ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletResponse response = sra.getResponse();
        if (distributedLock != null) {
            if (distributedLock.open()) {
                lockName = getLockName(distributedLock) + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
                RedisReentrantLock lock = new RedisReentrantLock(limitRedisTemplate, lockName, distributedLock.expire());
                try {
                    if (lock.tryLock(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)) {
                        log.debug("获取分布式锁:{}", lockName);
                        retVal = pjp.proceed();
                    } else {
                        log.debug("获取分布式锁超时,锁已被占用:{}", lockName);
                        throw new Exception("该方法正在执行,请勿重复操作");
                    }
                } catch (Exception e) {
                    throw e;
                } finally {
                    try {
                        lock.unlock();
                        log.debug("释放分布式锁:{}", lockName);
                    } catch (Exception e) {

                    }
                }
            } else {
                retVal = pjp.proceed();
            }

        } else {
            retVal = pjp.proceed();
        }
        return retVal;
    }

    private String getLockName(DistributedLock distributedLock) {
        final RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        final ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        StringBuffer stringBuffer = new StringBuffer("dislock:");
        switch (distributedLock.lock()) {
            case IP:
                stringBuffer.append(IpUtils.getIpAddrExt(request) + ":");
                break;
            case UNIQUEID:
                HttpServletRequest request = sra.getRequest();
                if (StringUtils.isNotBlank(distributedLock.field())) {
                    String uniqueId = request.getParameter(distributedLock.field());
                    if (StringUtils.isNotBlank(uniqueId)) {
                        stringBuffer.append(uniqueId + ":");
                    }
                }
                break;
        }
        return stringBuffer.toString();
    }
}
