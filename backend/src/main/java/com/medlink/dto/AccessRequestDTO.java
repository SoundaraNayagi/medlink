package com.medlink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessRequestDTO {
    private UUID id;
    private String requestStatus;
    private Boolean otpVerified;
    private LocalDateTime approvedAt;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}