package com.tab.enote_app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /*@Before("execution(* com.tab.enote_app.controller..*(..))")
    public void beforeController  (JoinPoint joinPoint) {

        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("calling :: {} :: {}", className, methodName);
    }

    @After("execution(* com.tab.enote_app.controller..*(..))")
    public void afterController  (JoinPoint joinPoint) {

        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("end calling :: {} :: {}", className, methodName);
    }*/
    
    @Around("execution(* com.tab.enote_app.controller..*(..))")
    public Object aroundController (ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("calling :: {} :: {}() :: {}ms", className,methodName);
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis()-start;
        log.info("end calling :: {} :: {}() :: {}ms", className, methodName,duration);

        return result;
    }

    @Around("execution(* com.tab.enote_app.service..*(..))")
    public Object aroundService (ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("calling :: {} :: {}() :: {}ms", className,methodName);
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis()-start;
        log.info("end calling :: {} :: {}() :: {}ms", className, methodName,duration);

        return result;
    }

    @Around("execution(* com.tab.enote_app.service_impl..*(..))")
    public Object aroundServiceImpl (ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("calling :: {} :: {}() :: {}ms", className,methodName);
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis()-start;
        log.info("end calling :: {} :: {}() :: {}ms", className, methodName,duration);

        return result;
    }

}
