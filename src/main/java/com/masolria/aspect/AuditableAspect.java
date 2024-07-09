package com.masolria.aspect;

import com.masolria.dto.UserDto;
import com.masolria.entity.Audit;
import com.masolria.entity.AuditType;

import com.masolria.listener.AppContextListener;
import com.masolria.repository.Jdbc.JdbcAuditRepository;
import com.masolria.util.UserStoreUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

@Aspect
public class AuditableAspect {
    //TODO logging  exception in aspect и поставить аннотацию на сервисы  и разобраться с логами
    private final JdbcAuditRepository auditRepository = (JdbcAuditRepository) AppContextListener.beyondContextAttrGet("jdbcAuditRepository");

    @Pointcut("within(@com.masolria.annotation.Auditable *)&& execution(* *(..))")
    public void annotatedBy() {
    }

    @Around("annotatedBy()")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
          System.out.println("audit worked");
        String methodCalled = joinPoint.getSignature().getName();
        UserDto userAuthorized = UserStoreUtil.getUserAuthorized();
        String email = userAuthorized != null ? userAuthorized.email() : "no email";
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