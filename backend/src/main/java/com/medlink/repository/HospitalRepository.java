package com.medlink.repository;

import com.medlink.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
    Optional<Hospital> findByHospitalName(String hospitalName);
    Optional<Hospital> findByRegistrationNumber(String registrationNumber);
}