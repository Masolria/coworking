package com.masolria.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Audit {
    private Long id;
    private String userEmail;
    private String method;
    private AuditType auditType;
    private LocalDateTime whenExecuted;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Audit audit)) return false;

        return Objects.equals(id, audit.id) && Objects.equals(userEmail, audit.userEmail) && Objects.equals(method, audit.method) && auditType == audit.auditType && Objects.equals(whenExecuted, audit.whenExecuted);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(userEmail);
        result = 31 * result + Objects.hashCode(method);
        result = 31 * result + Objects.hashCode(auditType);
        result = 31 * result + Objects.hashCode(whenExecuted);
        return result;
    }
}
