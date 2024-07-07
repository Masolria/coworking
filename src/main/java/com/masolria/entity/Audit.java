package com.masolria.entity;

import lombok.*;
import java.time.LocalDateTime;

@Builder
public record Audit(Long id,
                    String userEmail,
                    String message,
                    AuditType auditType,
                    LocalDateTime whenExecuted) {}
