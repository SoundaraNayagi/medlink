package com.medlink.service;

import com.medlink.entity.OtpRequest;
import com.medlink.entity.Patient;
import com.medlink.repository.OtpRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class OtpService {

    @Autowired
    private OtpRequestRepository otpRequestRepository;

    private static final int OTP_EXPIRY_MINUTES = 10;
    private static final int MAX_ATTEMPTS = 3;

    public String generateOtp(Patient patient) {
        String otp = String.format("%06d", new Random().nextInt(1000000));
        
        OtpRequest otpRequest = OtpRequest.builder()
                .patient(patient)
                .otpCode(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES))
                .verified(false)
                .attemptCount(0)
                .build();

        otpRequestRepository.save(otpRequest);
        log.info("OTP generated for patient: {}", patient.getId());
        return otp;
    }

    public boolean verifyOtp(Patient patient, String otpCode) {
        OtpRequest otpRequest = otpRequestRepository
                .findTopByPatientIdOrderByCreatedAtDesc(patient.getId())
                .orElse(null);

        if (otpRequest == null) {
            log.warn("No OTP found for patient: {}", patient.getId());
            return false;
        }

        if (otpRequest.getVerified()) {
            log.warn("OTP already verified for patient: {}", patient.getId());
            return false;
        }

        if (LocalDateTime.now().isAfter(otpRequest.getExpiresAt())) {
            log.warn("OTP expired for patient: {}", patient.getId());
            return false;
        }

        if (otpRequest.getAttemptCount() >= MAX_ATTEMPTS) {
            log.warn("Max OTP attempts exceeded for patient: {}", patient.getId());
            return false;
        }

        if (!otpRequest.getOtpCode().equals(otpCode)) {
            otpRequest.setAttemptCount(otpRequest.getAttemptCount() + 1);
            otpRequestRepository.save(otpRequest);
            log.warn("Invalid OTP attempt for patient: {}", patient.getId());
            return false;
        }

        otpRequest.setVerified(true);
        otpRequest.setVerifiedAt(LocalDateTime.now());
        otpRequestRepository.save(otpRequest);
        log.info("OTP verified for patient: {}", patient.getId());
        return true;
    }
}