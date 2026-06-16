package com.medlink.repository;

import com.medlink.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByPatientUniqueId(String patientUniqueId);
    Optional<Patient> findByUserId(UUID userId);
    Optional<Patient> findByUser_Phone(String phone);
}