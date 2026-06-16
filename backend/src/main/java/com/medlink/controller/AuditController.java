package com.medlink.controller;

import com.medlink.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/audit-logs")
@Tag(name = "Audit", description = "Audit logging endpoints")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Get audit logs for user")
    public ResponseEntity<?> getUserAuditLogs(@PathVariable UUID userId) {
        log.info("Fetching audit logs for user: {}", userId);
        return ResponseEntity.ok(auditService.getUserAuditLogs(userId));
    }

    @GetMapping("/action/{actionType}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Get audit logs by action type")
    public ResponseEntity<?> getAuditLogsByAction(@PathVariable String actionType) {
        log.info("Fetching audit logs for action: {}", actionType);
        return ResponseEntity.ok(auditService.getAuditLogs(actionType));
    }
}
