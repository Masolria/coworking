package com.masolria.aspect;

import com.masolria.dto.UserDto;
import com.masolria.entity.Audit;
import com.masolria.entity.AuditType;
import com.masolria.repository.Jdbc.AuditRepositoryJdbc;
import com.masolria.util.UserStoreUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

@Aspect
@RequiredArgsConstructor
public class AuditableAspect {
    //TODO logging  exception in aspect и поставить аннотацию на сервисы  и разобраться с логами
    private final AuditRepositoryJdbc auditRepository;

    @Pointcut("within(@com.masolria.annotation.Auditable *) && execution(* *(..))")
    public void annotatedBy() {
    }

    @Around("annotatedBy()")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodCalled = joinPoint.getSignature().getName();
        String email;
        UserDto userAuthorized = UserStoreUtil.getUserAuthorized();
        Object proceed;

        email = userAuthorized != null ? userAuthorized.email() : "no email";
        proceed = joinPoint.proceed();
        LocalDateTime when = LocalDateTime.now();
        Audit audit = Audit.builder()
                .userEmail(email)
                .message(methodCalled)
                .whenExecuted(when)
                .auditType(AuditType.SUCCESS)
                .build();
        auditRepository.save(audit);
        return proceed;
    }

}
