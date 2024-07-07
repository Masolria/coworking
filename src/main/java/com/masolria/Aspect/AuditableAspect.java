package com.masolria.Aspect;

import com.masolria.repository.Jdbc.AuditRepositoryJdbc;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@RequiredArgsConstructor
public class AuditableAspect {
    //TODO logging  exception in aspect и поставить аннотацию на сервисы  и разобраться с логами
    private final AuditRepositoryJdbc auditRepository;

    @Pointcut("")
    public void annotatedBy() {
    }

//    @Around("annotatedBy")
//    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable{
//        String message;
//        String methodCalled = joinPoint.getSignature().getName();
//        String email;
//        Object proceed;
//        try {
//            email =
//            proceed = joinPoint.proceed();
//        }
//        Audit audit = Audit.builder().userEmail().message().whenExecuted().auditType(AuditType.SUCCESS).build();
//         auditRepository.save();
//         return proceed;
//    }

//    @AfterThrowing()
//    public void onFailure(ProceedingJoinPoint joinPoint) {
//
//    }
}
