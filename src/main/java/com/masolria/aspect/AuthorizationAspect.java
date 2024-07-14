package com.masolria.aspect;

import com.masolria.exception.NotAuthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizationAspect {
   @Pointcut("within(@com.masolria.annotation.AuthRequired *)&&execution(* *(..))")
    public void annotatedBy(){}
  // TODO
//    @Around("annotatedBy()")
//    public void checkAuth(ProceedingJoinPoint joinPoint) throws Throwable{
//
//       if(){
//            joinPoint.proceed()
//       }else throw new NotAuthorizedException("Authorization required");
//    }
}
