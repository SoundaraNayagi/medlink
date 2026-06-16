package com.medlink.repository;

import com.medlink.entity.AccessRequest;
import com.medlink.entity.Patient;
import com.medlink.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccessRequestRepository extends JpaRepository<AccessRequest, UUID> {
    List<AccessRequest> findByPatientId(UUID patientId);
    List<AccessRequest> findByRequestingDoctorId(UUID doctorId);
    List<AccessRequest> findByRequestStatus(AccessRequest.RequestStatus status);
    Optional<AccessRequest> findByPatientIdAndRequestingDoctorId(UUID patientId, UUID doctorId);
}