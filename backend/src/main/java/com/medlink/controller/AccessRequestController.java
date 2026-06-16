package com.medlink.controller;

import com.medlink.dto.AccessRequestDTO;
import com.medlink.entity.AccessRequest;
import com.medlink.service.AccessRequestService;
import com.medlink.service.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/access-requests")
@Tag(name = "Access Requests", description = "Cross-hospital access request management")
public class AccessRequestController {

    @Autowired
    private AccessRequestService accessRequestService;

    @Autowired
    private OtpService otpService;

    @GetMapping("/pending")
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(summary = "Get pending access requests for patient")
    public ResponseEntity<List<AccessRequestDTO>> getPendingRequests(@RequestParam UUID patientId) {
        log.info("Fetching pending access requests for patient: {}", patientId);
        List<AccessRequest> requests = accessRequestService.getPendingAccessRequests(patientId);
        return ResponseEntity.ok(requests.stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/approved")
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(summary = "Get approved access requests for patient")
    public ResponseEntity<List<AccessRequestDTO>> getApprovedRequests(@RequestParam UUID patientId) {
        log.info("Fetching approved access requests for patient: {}", patientId);
        List<AccessRequest> requests = accessRequestService.getApprovedAccessRequests(patientId);
        return ResponseEntity.ok(requests.stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @PostMapping("/{requestId}/verify-otp")
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(summary = "Verify OTP and approve access request")
    public ResponseEntity<String> verifyOtp(@PathVariable UUID requestId, @RequestBody OtpVerificationRequest request) {
        log.info("Verifying OTP for access request: {}", requestId);
        boolean verified = accessRequestService.verifyAndApproveAccess(requestId, request.getOtpCode(), otpService);
        if (verified) {
            return ResponseEntity.ok("Access approved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP verification failed");
        }
    }

    @PostMapping("/{requestId}/reject")
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(summary = "Reject access request")
    public ResponseEntity<String> rejectRequest(@PathVariable UUID requestId, @RequestBody RejectRequest rejectRequest) {
        log.info("Rejecting access request: {}", requestId);
        accessRequestService.rejectAccessRequest(requestId, rejectRequest.getReason());
        return ResponseEntity.ok("Access request rejected");
    }

    private AccessRequestDTO mapToDTO(AccessRequest request) {
        return AccessRequestDTO.builder()
                .id(request.getId())
                .requestStatus(request.getRequestStatus().toString())
                .otpVerified(request.getOtpVerified())
                .approvedAt(request.getApprovedAt())
                .expiresAt(request.getExpiresAt())
                .createdAt(request.getCreatedAt())
                .build();
    }
}

class OtpVerificationRequest {
    private String otpCode;

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
}

class RejectRequest {
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}