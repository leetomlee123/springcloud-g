package com.example.invoke.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author lee
 */
@Aspect
@Component
public class CacheAspect {

    @Pointcut("execution(public * com.example.invoke.serviceimpl..*.*(..))")
    public void pointCut() {
    }

    @After(value = "pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取当前切点方法对象
        Method method = methodSignature.getMethod();


    }

    @AfterReturning(returning = "rv", pointcut = "@annotation(com.example.invoke.annotation.DataCache)")
    public Object afterExec(JoinPoint joinPoint, Object rv) {

        return rv;
    }

}
