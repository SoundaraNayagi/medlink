package com.medlink.controller;

import com.medlink.dto.PatientDTO;
import com.medlink.entity.Patient;
import com.medlink.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/patients")
@Tag(name = "Patients", description = "Patient management endpoints")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR') or hasRole('HOSPITAL_ADMIN') or hasRole('SUPER_ADMIN')")
    @Operation(summary = "Get patient profile")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable UUID id) {
        log.info("Fetching patient: {}", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(summary = "Update patient profile")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable UUID id, @RequestBody PatientDTO patientDTO) {
        log.info("Updating patient: {}", id);
        return ResponseEntity.ok(patientService.updatePatient(id, patientDTO));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('HOSPITAL_ADMIN')")
    @Operation(summary = "Search patient by phone number")
    public ResponseEntity<PatientDTO> searchPatient(@RequestParam String phone) {
        log.info("Searching patient by phone: {}", phone);
        return ResponseEntity.ok(patientService.searchPatientByPhone(phone));
    }
}

package com.medlink.service;

import com.medlink.dto.PatientDTO;
import com.medlink.entity.Patient;
import com.medlink.entity.User;
import com.medlink.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public PatientDTO getPatientById(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return mapToDTO(patient);
    }

    public PatientDTO updatePatient(UUID id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (patientDTO.getBloodGroup() != null) {
            patient.setBloodGroup(patientDTO.getBloodGroup());
        }
        if (patientDTO.getAddress() != null) {
            patient.setAddress(patientDTO.getAddress());
        }
        if (patientDTO.getEmergencyContact() != null) {
            patient.setEmergencyContact(patientDTO.getEmergencyContact());
        }

        Patient updated = patientRepository.save(patient);
        log.info("Patient updated: {}", id);
        return mapToDTO(updated);
    }

    public PatientDTO searchPatientByPhone(String phone) {
        Patient patient = patientRepository.findByUser_Phone(phone)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return mapToDTO(patient);
    }

    private PatientDTO mapToDTO(Patient patient) {
        return PatientDTO.builder()
                .id(patient.getId())
                .patientUniqueId(patient.getPatientUniqueId())
                .aadhaarNumber(patient.getAadhaarNumber())
                .bloodGroup(patient.getBloodGroup())
                .gender(patient.getGender())
                .dateOfBirth(patient.getDateOfBirth() != null ? patient.getDateOfBirth().toString() : null)
                .emergencyContact(patient.getEmergencyContact())
                .emergencyContactName(patient.getEmergencyContactName())
                .address(patient.getAddress())
                .build();
    }
}