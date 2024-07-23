package com.masolria.aspect;

import com.masolria.dto.UserDto;
import com.masolria.entity.Audit;
import com.masolria.entity.AuditType;
import com.masolria.repository.Jdbc.JdbcAuditRepository;
import com.masolria.util.UserStoreUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditableAspect {
    private final JdbcAuditRepository auditRepository;

    @Pointcut("within(@com.masolria.annotation.Auditable *)&& execution(* *(..))")
    public void annotatedBy() {
    }

    @Around("annotatedBy()")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
          System.out.println("audit worked");
        String methodCalled = joinPoint.getSignature().getName();
        UserDto userAuthorized = UserStoreUtil.getUserAuthorized();
        String email = userAuthorized != null ? userAuthorized.getEmail() : "no email";
        LocalDateTime when = LocalDateTime.now();
        Object result = joinPoint.proceed();
        Audit audit = Audit.builder()
                .userEmail(email)
                .method(methodCalled)
                .auditType(AuditType.SUCCESS)
                .whenExecuted(when)
                .build();

        auditRepository.save(audit);
       return result;
    }
}