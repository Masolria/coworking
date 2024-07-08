package com.masolria.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
@Slf4j
public class LoggableAspect {

    @Pointcut("(within(@com.masolria.annotation.Loggable *)&& execution(* *(..))|| execution(@com.masolria.annotation.Auditable * *(..)))")
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