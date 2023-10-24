package com.training.helper.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This class handles AOP
 */
@Aspect
@Component
public class AspectForException {
    private static final Logger logger = LoggerFactory.getLogger(AspectForException.class);


    @Before("execution(* com.training.helper.controller.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("Method started: " + method);
    }


    @Around("execution(* com.training.helper.controller.*.*(..))")
    public Object handleExceptionAndLogMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String method = proceedingJoinPoint.getSignature().toShortString();
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
            logger.info("Method finished: " + method);
        } catch (Exception exception) {
            logger.error("Exception occurred in method: " + method + ",Message: " + exception.getMessage());
            throw exception;
        }

        return result;
    }
}
