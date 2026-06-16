package com.medlink.service;

import com.medlink.entity.AuditLog;
import com.medlink.entity.User;
import com.medlink.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logAction(User user, String actionType, String entityName, UUID entityId, String oldValue, String newValue, String ipAddress, String userAgent) {
        AuditLog auditLog = AuditLog.builder()
                .user(user)
                .actionType(actionType)
                .entityName(entityName)
                .entityId(entityId)
                .oldValue(oldValue)
                .newValue(newValue)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
        log.info("Audit log recorded: {} - {}", actionType, entityName);
    }

    public List<AuditLog> getAuditLogs(String actionType) {
        return auditLogRepository.findByActionType(actionType);
    }

    public List<AuditLog> getUserAuditLogs(UUID userId) {
        return auditLogRepository.findByUserId(userId);
    }
}