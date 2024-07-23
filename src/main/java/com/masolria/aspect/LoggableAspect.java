package com.masolria.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggableAspect {

    @Pointcut("within(@com.masolria.annotation.Loggable *)&& execution(* *(..))")
    public void loggableMethods() {
    }

    @Around("loggableMethods()")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("log working");
        String signature = proceedingJoinPoint.getSignature().
                toShortString();
        long start = System.currentTimeMillis();

        Object proceed = proceedingJoinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;
        log.info("Execution of {} method finished. Execution time is {} ms", signature, executionTime);
        return proceed;
    }
}