package com.medlink.service;

import com.medlink.entity.AccessRequest;
import com.medlink.entity.Doctor;
import com.medlink.entity.OtpRequest;
import com.medlink.entity.Patient;
import com.medlink.repository.AccessRequestRepository;
import com.medlink.repository.OtpRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class AccessRequestService {

    @Autowired
    private AccessRequestRepository accessRequestRepository;

    @Autowired
    private OtpRequestRepository otpRequestRepository;

    private static final int ACCESS_VALIDITY_DAYS = 30;

    public AccessRequest createAccessRequest(Patient patient, Doctor requestingDoctor) {
        AccessRequest accessRequest = AccessRequest.builder()
                .patient(patient)
                .requestingDoctor(requestingDoctor)
                .requestingHospital(requestingDoctor.getHospital())
                .requestStatus(AccessRequest.RequestStatus.PENDING)
                .otpVerified(false)
                .build();

        return accessRequestRepository.save(accessRequest);
    }

    public boolean verifyAndApproveAccess(UUID accessRequestId, String otpCode, OtpService otpService) {
        Optional<AccessRequest> accessRequest = accessRequestRepository.findById(accessRequestId);
        if (accessRequest.isEmpty()) {
            return false;
        }

        AccessRequest request = accessRequest.get();
        Patient patient = request.getPatient();

        if (otpService.verifyOtp(patient, otpCode)) {
            request.setOtpVerified(true);
            request.setRequestStatus(AccessRequest.RequestStatus.APPROVED);
            request.setApprovedAt(LocalDateTime.now());
            request.setExpiresAt(LocalDateTime.now().plusDays(ACCESS_VALIDITY_DAYS));
            accessRequestRepository.save(request);
            log.info("Access request approved: {}", accessRequestId);
            return true;
        }
        return false;
    }

    public void rejectAccessRequest(UUID accessRequestId, String reason) {
        Optional<AccessRequest> accessRequest = accessRequestRepository.findById(accessRequestId);
        if (accessRequest.isPresent()) {
            AccessRequest request = accessRequest.get();
            request.setRequestStatus(AccessRequest.RequestStatus.REJECTED);
            request.setRejectionReason(reason);
            accessRequestRepository.save(request);
            log.info("Access request rejected: {}", accessRequestId);
        }
    }

    public List<AccessRequest> getPendingAccessRequests(UUID patientId) {
        return accessRequestRepository.findByPatientId(patientId).stream()
                .filter(req -> req.getRequestStatus() == AccessRequest.RequestStatus.PENDING)
                .toList();
    }

    public List<AccessRequest> getApprovedAccessRequests(UUID patientId) {
        return accessRequestRepository.findByPatientId(patientId).stream()
                .filter(req -> req.getRequestStatus() == AccessRequest.RequestStatus.APPROVED &&
                        req.getExpiresAt() != null && 
                        LocalDateTime.now().isBefore(req.getExpiresAt()))
                .toList();
    }
}