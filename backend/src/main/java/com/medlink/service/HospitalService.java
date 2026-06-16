package com.medlink.service;

import com.medlink.entity.Hospital;
import com.medlink.repository.HospitalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Hospital getHospitalById(UUID id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));
    }

    public Hospital createHospital(Hospital hospital) {
        Hospital saved = hospitalRepository.save(hospital);
        log.info("Hospital created: {}", saved.getId());
        return saved;
    }

    public Hospital updateHospital(UUID id, Hospital hospitalDetails) {
        Hospital hospital = getHospitalById(id);
        
        if (hospitalDetails.getHospitalName() != null) {
            hospital.setHospitalName(hospitalDetails.getHospitalName());
        }
        if (hospitalDetails.getAddress() != null) {
            hospital.setAddress(hospitalDetails.getAddress());
        }
        if (hospitalDetails.getCity() != null) {
            hospital.setCity(hospitalDetails.getCity());
        }
        if (hospitalDetails.getPhone() != null) {
            hospital.setPhone(hospitalDetails.getPhone());
        }
        if (hospitalDetails.getEmail() != null) {
            hospital.setEmail(hospitalDetails.getEmail());
        }

        Hospital updated = hospitalRepository.save(hospital);
        log.info("Hospital updated: {}", id);
        return updated;
    }
}
