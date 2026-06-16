package com.medlink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "access_requests")
public class AccessRequest {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesting_hospital_id", nullable = false)
    private Hospital requestingHospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesting_doctor_id", nullable = false)
    private Doctor requestingDoctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus requestStatus = RequestStatus.PENDING;

    @Column(nullable = false)
    private Boolean otpVerified = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "otp_id")
    private OtpRequest otp;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum RequestStatus {
        PENDING, APPROVED, REJECTED, EXPIRED
    }
}