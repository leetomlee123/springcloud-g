package com.example.invoke.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author lee
 */
@Aspect
@Component
@Order(10)
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    private ThreadLocal<Long> curtime = new ThreadLocal();

    @Pointcut("execution(public * com.example.invoke.web..*.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {

        curtime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        LOG.info("URL : " + request.getRequestURL().toString());
        LOG.info("HTTP_METHOD : " + request.getMethod());
        LOG.info("IP : " + request.getRemoteAddr());
        LOG.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOG.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "pointCut()")
    public void doAfterReturning(Object ret) throws Throwable {

        // 处理完请求，返回内容
        LOG.info("RESPONSE : " + ret);
        LOG.info("SPEND TIME : " + (System.currentTimeMillis() - curtime.get()));
    }


}

