package com.medlink.repository;

import com.medlink.entity.OtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRequestRepository extends JpaRepository<OtpRequest, UUID> {
    Optional<OtpRequest> findTopByPatientIdOrderByCreatedAtDesc(UUID patientId);
}