package com.example.invoke.aop;

import com.example.invoke.annotation.TargetDataSource;
import com.example.invoke.common.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author lee
 */
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Pointcut("execution(public * com.example.invoke.serviceimpl..*.*(..))")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object doBeforeWithSlave(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取当前切点方法对象
        Method method = methodSignature.getMethod();

        /*
        判断是否为借口方法
         */
        if (method.getDeclaringClass().isInterface()) {
            try {
                //获取实际类型的方法对象
                method = joinPoint.getTarget().getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                LOG.error("方法不存在！", e);
            }
        }
        TargetDataSource annotation = method.getDeclaringClass().getAnnotation(TargetDataSource.class);
        if (null == annotation) {
            DynamicDataSourceContextHolder.setSlave();
        } else {
            DynamicDataSourceContextHolder.set(annotation.dataSourceKey());
        }
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        DynamicDataSourceContextHolder.clear();
        return proceed;
    }

}