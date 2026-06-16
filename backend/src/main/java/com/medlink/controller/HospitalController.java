package com.medlink.controller;

import com.medlink.entity.Hospital;
import com.medlink.service.HospitalService;
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

@Slf4j
@RestController
@RequestMapping("/hospitals")
@Tag(name = "Hospitals", description = "Hospital management endpoints")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    @Operation(summary = "List all hospitals")
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        log.info("Fetching all hospitals");
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get hospital details")
    public ResponseEntity<Hospital> getHospital(@PathVariable UUID id) {
        log.info("Fetching hospital: {}", id);
        return ResponseEntity.ok(hospitalService.getHospitalById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Create hospital")
    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital hospital) {
        log.info("Creating hospital: {}", hospital.getHospitalName());
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.createHospital(hospital));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN')")
    @Operation(summary = "Update hospital")
    public ResponseEntity<Hospital> updateHospital(@PathVariable UUID id, @RequestBody Hospital hospital) {
        log.info("Updating hospital: {}", id);
        return ResponseEntity.ok(hospitalService.updateHospital(id, hospital));
    }
}
