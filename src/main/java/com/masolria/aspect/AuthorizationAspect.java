package com.masolria.aspect;

import com.masolria.exception.NotAuthorizedException;
import com.masolria.util.UserStoreUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizationAspect {
   @Pointcut("within(@com.masolria.annotation.AuthRequired *)&&execution(* *(..))")
    public void annotatedBy(){}

    @Around("annotatedBy()")
    public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable{
       Object proceed = joinPoint.proceed();
       if(UserStoreUtil.getUserAuthorized()!=null){
            return proceed;
       }else throw new NotAuthorizedException("Authorization required");
    }
}
